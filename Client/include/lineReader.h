#ifndef LINE_READER__
#define LINE_READER__
#include "connectionHandler.h"
#include "messageEncoder.h"
#include <mutex>
class lineReader{
public:
    lineReader(ConnectionHandler &handler, messageEncoder encoder,std::mutex &mutex);
    void operator()();
private:
    messageEncoder encoder;
    ConnectionHandler &handler;
    mutex &mutexLock;
};



#endif