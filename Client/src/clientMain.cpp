#include <connectionHandler.h>
#include <lineReader.h>
#include <thread>
#include <messageDecoder.h>
#include <mutex>
int main (int argc, char *argv[]) {
    if (argc < 3) {
        std::cerr << "Usage: " << argv[0] << " host port" << std::endl << std::endl;
        return -1;
    }
    std::string host = argv[1];
    short port = atoi(argv[2]);

    ConnectionHandler connectionHandler(host, port);
    if (!connectionHandler.connect()) {
        std::cerr << "Cannot connect to " << host << ":" << port << std::endl;
        return 1;
    }

    //thread to send socket
    std::mutex mutex;
    messageEncoder encoder(connectionHandler);
    lineReader reader(connectionHandler, encoder ,mutex);
    std::thread readerThread(&lineReader::operator(), reader);
    //thread to receive socket
    messageDecoder decoder(connectionHandler,mutex);
    std::thread decoderThread(&messageDecoder::operator(), decoder);


    decoderThread.join();
    readerThread.join();

    return 1;
}