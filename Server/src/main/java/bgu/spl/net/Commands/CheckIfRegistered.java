package bgu.spl.net.Commands;

import bgu.spl.net.BaseCommand.Command;
import bgu.spl.net.BaseCommand.ClientCommand;
import bgu.spl.net.api.User;



public class CheckIfRegistered extends ClientCommand {

    public CheckIfRegistered(int courseNum) {
        super(courseNum);
        opcode = 9;
    }

    @Override
    public Command act(User user) {
        if (!user.isLoggedIn() || user.isAdmin())
            return new Error(opcode);

        boolean isReg = user.isRegister(CourseNumber);
        String message ="REGISTERED\0";
        if (!isReg)
            message = "NOT " + message ;
        return new Acknowledgement(opcode ,message);
    }
}
