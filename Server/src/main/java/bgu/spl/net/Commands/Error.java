package bgu.spl.net.Commands;

import bgu.spl.net.BaseCommand.Command;
import bgu.spl.net.BaseCommand.ServerCommand;
import bgu.spl.net.api.User;

public class Error extends ServerCommand {

    public Error(short opcodeOfError) {
        super(opcodeOfError);
        opcode = 13;
    }

    @Override
    public Command act(User user) {
        return null;
    }
}
