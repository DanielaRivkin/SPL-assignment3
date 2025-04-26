CFLAGS:=-c -Wall -Weffc++ -g -std=c++11 -Iinclude -lboost_system -lboost_thread -pthread
LDFLAGS:=-lboost_system -lboost_thread -pthread

all: client
	g++ -o bin/BGRSclient bin/clientMain.o bin/connectionHandler.o bin/lineReader.o bin/messageDecoder.o bin/messageEncoder.o $(LDFLAGS)

client: bin/clientMain.o bin/connectionHandler.o bin/lineReader.o bin/messageDecoder.o bin/messageEncoder.o
	
bin/clientMain.o: src/clientMain.cpp
	g++ $(CFLAGS) -o bin/clientMain.o src/clientMain.cpp

bin/connectionHandler.o: src/connectionHandler.cpp
	g++ $(CFLAGS) -o bin/connectionHandler.o src/connectionHandler.cpp

bin/lineReader.o: src/lineReader.cpp
	g++ $(CFLAGS) -o bin/lineReader.o src/lineReader.cpp

bin/messageDecoder.o: src/messageDecoder.cpp
	g++ $(CFLAGS) -o bin/messageDecoder.o src/messageDecoder.cpp

bin/messageEncoder.o: src/messageEncoder.cpp
	g++ $(CFLAGS) -o bin/messageEncoder.o src/messageEncoder.cpp

.PHONY: clean
clean:
	rm -f bin/* BGRSclient
