package bgu.spl.net.Commands;



import bgu.spl.net.BaseCommand.Command;
import bgu.spl.net.BaseCommand.ClientCommand;
import bgu.spl.net.api.User;


public class StudentRegister extends ClientCommand {

    public StudentRegister(String userName, String password) {
        super(userName,password);
        opcode = 2;
    }

    @Override
    public Command act(User user) {
        if (user.isLoggedIn())
            return new Error(opcode);
        boolean regSuccesses = database.addUser(new User(username,password,false));
        if (!regSuccesses) {
            return new Error(opcode);
        }
        return new Acknowledgement(opcode);
    }
}
