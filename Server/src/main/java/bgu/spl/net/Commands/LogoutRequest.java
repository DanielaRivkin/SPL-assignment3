package bgu.spl.net.Commands;



import bgu.spl.net.BaseCommand.Command;
import bgu.spl.net.BaseCommand.ClientCommand;
import bgu.spl.net.api.User;

public class LogoutRequest extends ClientCommand {

    public LogoutRequest(){
        super();
        opcode = 4;
    }
    @Override
    public Command act(User user) {
        if (!user.isLoggedIn())
            return new Error(opcode);
        database.getUser(user.getUsername()).setLoggedIn(false);
        user.setLoggedIn(false);
        return new Acknowledgement(opcode);
    }
}
