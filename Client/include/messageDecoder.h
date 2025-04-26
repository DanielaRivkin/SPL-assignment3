//
// Created by spl211 on 02/01/2021.
//

#ifndef ASSIGNMENT3_MESSAGEDECODER_H
#define ASSIGNMENT3_MESSAGEDECODER_H


#include "connectionHandler.h"
#include <mutex>
class messageDecoder {
public:
    messageDecoder(ConnectionHandler &handler,std::mutex &mutex);
    void operator()();
    void decoder(char line[]);
private:
    ConnectionHandler &handler;
    std::mutex &mutexLock;
};


#endif //ASSIGNMENT3_MESSAGEDECODER_H
