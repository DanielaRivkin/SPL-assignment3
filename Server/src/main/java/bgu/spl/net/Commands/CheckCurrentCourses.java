package bgu.spl.net.Commands;

import bgu.spl.net.BaseCommand.Command;
import bgu.spl.net.BaseCommand.ClientCommand;
import bgu.spl.net.api.User;

public class CheckCurrentCourses extends ClientCommand {
    public CheckCurrentCourses() {
        super();
        opcode = 11;
    }

    @Override
    public Command act(User user) {
        if ( !user.isLoggedIn() || user.isAdmin())
            return new Error(opcode);

        user.getCourseList().sort(database.CourseLinesComparator());
        String output = user.getCourseList().toString().replaceAll("\\s","");
        output += "\0";
        return new Acknowledgement(opcode, output);
    }
}
