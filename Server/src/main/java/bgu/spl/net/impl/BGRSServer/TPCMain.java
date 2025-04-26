package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.BGRS_EncoderDecoder;
import bgu.spl.net.BGRS_Protocol;
import bgu.spl.net.api.Database;
import bgu.spl.net.srv.threadPerClientServer;

import java.io.FileNotFoundException;
import java.util.function.Supplier;

public class TPCMain implements Runnable {

    private int port;

    public TPCMain(int port) {
        this.port = port;
    }

    public void run() {
            new threadPerClientServer(port,
                    new Supplier() {
                        @Override
                        public Object get() {
                            return new BGRS_Protocol();
                        }
                    }, new Supplier() {
                @Override
                public Object get() {
                    return new BGRS_EncoderDecoder();
                }
            }).serve();
    }

    public static void main(String[] args) {
        int port = Integer.parseInt(args[0]);
        Database.getInstance();
        new TPCMain(port).run();
    }
}
