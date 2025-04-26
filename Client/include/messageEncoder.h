//
// Created by spl211 on 02/01/2021.
//

#ifndef ASSIGNMENT3_MESSAGEENCODER_H
#define ASSIGNMENT3_MESSAGEENCODER_H
#include <string>
#include "connectionHandler.h"

using namespace std;

class messageEncoder {
public:
    messageEncoder(ConnectionHandler &handler);
    bool encode(const string& command);

private:
    ConnectionHandler &handler;
    //from command to opcode string short
    short opcodeNumber(string opcode);
    //opcode to byte
    bool opcodeSender(short opcode);


};


#endif //ASSIGNMENT3_MESSAGEENCODER_H
