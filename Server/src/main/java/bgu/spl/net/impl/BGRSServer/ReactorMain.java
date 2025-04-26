package bgu.spl.net.impl.BGRSServer;

import bgu.spl.net.BGRS_EncoderDecoder;
import bgu.spl.net.BGRS_Protocol;
import bgu.spl.net.api.Database;
import bgu.spl.net.srv.Reactor;

public class ReactorMain implements Runnable{
    private final int port;
    private final int numOfThread;

    public ReactorMain(String[] args){
        this.port = Integer.parseInt(args[0]);
        this.numOfThread = Integer.parseInt(args[1]);
    }
    @Override
    public void run() {
        new Reactor<>(numOfThread, port, BGRS_Protocol::new,
                BGRS_EncoderDecoder::new).serve();
    }

    public static void main(String[] args) {
        Database.getInstance();
        new ReactorMain(args).run();
    }

}
