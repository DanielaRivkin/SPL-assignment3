package bgu.spl.net.BaseCommand;


import bgu.spl.net.api.Database;
import bgu.spl.net.api.User;

public abstract class ClientCommand implements Command {
    protected String username;
    protected String password;
    protected int CourseNumber;
    protected short opcode;

    protected Database database = Database.getInstance();

    protected ClientCommand(){}

    protected ClientCommand(String username, String password){
        this.username = username;
        this.password = password;
    }

    protected ClientCommand(int courseNumber){
        this.CourseNumber = courseNumber;
    }

    protected ClientCommand(String username){
        this.username = username;
    }


    public abstract Command act(User user);

}
