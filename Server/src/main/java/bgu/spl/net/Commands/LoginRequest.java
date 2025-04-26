package bgu.spl.net.Commands;


import bgu.spl.net.BaseCommand.ClientCommand;
import bgu.spl.net.BaseCommand.Command;
import bgu.spl.net.api.User;

public class LoginRequest extends ClientCommand {

    public LoginRequest(String userName, String password) {
        super(userName, password);
        opcode = 3;
    }

    @Override
    public Command act(User user) {
        if (user.isLoggedIn())
            return new Error(opcode);
            User tempUser = database.getUser(username);
            if (tempUser == null || !tempUser.getPassword().equals(password)||tempUser.isLoggedIn()) {
                return new Error(opcode);
            }
            tempUser.setLoggedIn(true);
            user.init(username, password, tempUser.isAdmin());

        return new Acknowledgement(opcode);
    }
}
