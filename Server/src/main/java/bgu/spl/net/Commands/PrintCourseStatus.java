package bgu.spl.net.Commands;

import bgu.spl.net.BaseCommand.Command;
import bgu.spl.net.BaseCommand.ClientCommand;
import bgu.spl.net.api.User;

public class PrintCourseStatus extends ClientCommand {

    public PrintCourseStatus(int courseNum) {
        super(courseNum);
        opcode = 7;
    }

    @Override
    public Command act(User user) {
        if (!user.isLoggedIn()||!user.isAdmin() )
            return new Error(opcode);
        String output = database.getCourseStatus(CourseNumber);
        if (output == null)
            return new Error(opcode);

        return new Acknowledgement(opcode ,output);
    }
}
