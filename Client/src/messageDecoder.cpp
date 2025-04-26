#include "messageDecoder.h"

using namespace std;

messageDecoder::messageDecoder(ConnectionHandler &handler ,std::mutex &mutexLock) : handler(handler),mutexLock(mutexLock) {}

void messageDecoder::operator()() {
    while (!handler.shouldTerminate()) {
        string answer;
        char opcodes[4];
        if (handler.getBytes(opcodes, 4)) {
            decoder(opcodes);
        } else {
            handler.terminateClient();
        }
    }
}

//12 1 [optional] \0
//12/13 [opcode] [opional] \0
//01 02 00 01 00 = 12 1 \0
void messageDecoder::decoder(char opcodes[]) {
    short res = (short) ((opcodes[0] & 0xff) << 8);
    res += (short) (opcodes[1] & 0xff);
    short opcode = (short) ((opcodes[2] & 0xff) << 8);
    opcode += (short) (opcodes[3] & 0xff);
    switch (res) {
        case 12:
            if (((opcode > 5) & (opcode < 10))|| opcode == 11) {
                string args;
                handler.getLine(args);
                cout << "ACK " << opcode << endl;
                cout << args << endl;

            } else if (opcode == 4) {
                handler.terminateClient();
                cout << "ACK " << opcode << endl;
            } else {
                cout << "ACK " << opcode << endl;
            }
            break;
        case 13: {
            cout << "ERROR " << opcode << endl;
            break;
        }
        default:
            //this is for debugging
            cout << "Unknown opcode" << endl;
    }

}
