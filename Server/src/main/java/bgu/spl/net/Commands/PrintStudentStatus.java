package bgu.spl.net.Commands;

import bgu.spl.net.BaseCommand.Command;
import bgu.spl.net.BaseCommand.ClientCommand;
import bgu.spl.net.api.User;

public class PrintStudentStatus extends ClientCommand {

    public PrintStudentStatus(String userName) {
        super(userName);
        opcode = 8;
    }

    @Override
    public Command act(User user) {
        if ( !user.isAdmin()||!user.isLoggedIn())
            return new Error(opcode);

        User currentUser = database.getUser(username);
        if (currentUser == null)
            return new Error(opcode );
        String message = currentUser.getStudentStatus();
        if (message == null)
            return new Error(opcode);
        return new Acknowledgement(opcode, message);
    }
}
