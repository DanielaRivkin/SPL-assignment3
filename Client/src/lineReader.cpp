#include <lineReader.h>
#include "../include/messageDecoder.h"
lineReader::lineReader(ConnectionHandler &handler, messageEncoder encoder ,std::mutex &mutexLock) : encoder(encoder), handler(handler),mutexLock(mutexLock) {}

void lineReader::operator()() {
    while (!handler.shouldTerminate()) {
        const short bufsize = 1024;
        char buf[bufsize];
        std::cin.getline(buf, bufsize);
        std::string line(buf);
        if (!encoder.encode(line)) {
            handler.terminateClient();
        }
        if (line == "LOGOUT"){
            mutexLock.lock();
            string answer;
            char opcodes[4];
            if (handler.getBytes(opcodes, 4)) {
                messageDecoder dec(handler,mutexLock);
                dec.decoder(opcodes);
            } else {
                handler.terminateClient();
            }
            mutexLock.unlock();
        }
    }
}