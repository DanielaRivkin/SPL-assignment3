package bgu.spl.net.Commands;

import bgu.spl.net.BaseCommand.Command;
import bgu.spl.net.BaseCommand.ClientCommand;
import bgu.spl.net.api.User;

import java.util.List;

public class CheckKdamCourse extends ClientCommand {

    public CheckKdamCourse(int courseNum) {
        super(courseNum);
        opcode = 6 ;
    }

    @Override
    public Command act(User user) {
        if(!user.isLoggedIn() || user.isAdmin())
            return new Error(opcode);

        List <Integer> kdams = database.getKdams(CourseNumber);
        if (kdams == null)
            return new Error(opcode);
        String message = kdams.toString().replaceAll("\\s","")+'\0';
        return new Acknowledgement(opcode , message);
    }

}
