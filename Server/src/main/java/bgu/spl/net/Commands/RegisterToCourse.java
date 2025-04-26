package bgu.spl.net.Commands;

import bgu.spl.net.BaseCommand.Command;
import bgu.spl.net.BaseCommand.ClientCommand;
import bgu.spl.net.api.User;

public class RegisterToCourse extends ClientCommand {

    public RegisterToCourse (int courseNum){
        super(courseNum);
        opcode = 5;
    }
    @Override
    public Command act(User user) {
        if (user.isAdmin()||!user.isLoggedIn())
            return new Error(opcode);

        boolean kdamCheck = database.kdamCheck(user,CourseNumber);
        if (!kdamCheck)
            return new Error(opcode);
        if (user.isRegister(CourseNumber))
            return new Error(opcode);
        boolean regSuccess = database.registerToCourse(user,CourseNumber);
        if (!regSuccess)
            return new Error(opcode );

        user.addCourse(CourseNumber);
        return new Acknowledgement(opcode);
    }
}
