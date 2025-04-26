//
// Created by spl211 on 02/01/2021.
//

#include <sstream>
#include <vector>
#include "messageEncoder.h"

messageEncoder::messageEncoder(ConnectionHandler &handler) : handler(handler) {}

bool messageEncoder::encode(const string &command) {
    istringstream ss(command);
    vector<string> commandArguments;
    string argument;
    bool sending = true;
    while (ss >> argument) {
        commandArguments.push_back(argument);
    }
    if (!commandArguments.empty()) {
        short opcode = opcodeNumber(commandArguments[0]);
        if (opcode != -1) {
            if (opcodeSender(opcode)) {
                //case there are no more bytes
                if ((opcode == 4) | (opcode == 11)) {
                    return true;
                } else if((opcode >=5 && opcode<=10) && (opcode!=8)){
                    short courseNumber = std::stoi(commandArguments[1]);
                    //case there is only 2 byte to send
                    opcodeSender(courseNumber);
                    return true;
                }
                else {
                    //other case send all the argument
                    for (unsigned i = 1; (i < commandArguments.size()) & (sending); i++) {
                        sending = handler.sendLine(commandArguments[i]);
                    }
                }
            } else {
                sending = false;
            }
        }
    }
    return sending;
}


bool messageEncoder::opcodeSender(const short opcode) {
    //this is from the website
    char opcodeBytes[2];
    opcodeBytes[0] = (((opcode >> 8) & 0xFF));
    opcodeBytes[1] = (opcode & 0xFF);
    return handler.sendBytes(opcodeBytes, 2);
}

short messageEncoder::opcodeNumber(string opcode) {
//a lot of if conditions and returns
    if (opcode == "ADMINREG")
        return 1;
    if (opcode == "STUDENTREG")
        return 2;
    if (opcode == "LOGIN")
        return 3;
    if (opcode == "LOGOUT")
        return 4;
    if (opcode == "COURSEREG")
        return 5;
    if (opcode == "KDAMCHECK")
        return 6;
    if (opcode == "COURSESTAT")
        return 7;
    if (opcode == "STUDENTSTAT")
        return 8;
    if (opcode == "ISREGISTERED")
        return 9;
    if (opcode == "UNREGISTER")
        return 10;
    if (opcode == "MYCOURSES")
        return 11;
    return -1;
}