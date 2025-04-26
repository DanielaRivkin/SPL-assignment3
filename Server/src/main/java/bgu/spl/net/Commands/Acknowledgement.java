package bgu.spl.net.Commands;

import bgu.spl.net.BaseCommand.ServerCommand;
import bgu.spl.net.BaseCommand.ClientCommand;
import bgu.spl.net.api.User;


public class Acknowledgement extends ServerCommand {

    public Acknowledgement(short opcodeOfResponse, String message){
       super(opcodeOfResponse,message);
       opcode = 12;

    }

    public Acknowledgement(short opcodeOfResponse) {
        super(opcodeOfResponse);
        opcode = 12;
    }

    @Override
    public ClientCommand act(User user) {
        return null;
    }

}
