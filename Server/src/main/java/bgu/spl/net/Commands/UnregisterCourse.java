package bgu.spl.net.Commands;

import bgu.spl.net.BaseCommand.Command;
import bgu.spl.net.BaseCommand.ClientCommand;
import bgu.spl.net.api.User;


public class UnregisterCourse extends ClientCommand {

    public UnregisterCourse(int courseNum) {
        super(courseNum);
        //System.out.println(courseNum);
        opcode = 10;
    }

    @Override
    public Command act(User user) {
        if (user.isAdmin()||!user.isLoggedIn())
            return new Error(opcode);

        if (!user.isRegister(CourseNumber))
            return new Error(opcode);
        synchronized (user.getCourseList()) {
            boolean unregSucc = database.unregister(user, CourseNumber);
            if (!unregSucc)
                return new Error(opcode);
        }
        return new Acknowledgement(opcode);
    }
}
