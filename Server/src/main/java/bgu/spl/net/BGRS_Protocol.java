package bgu.spl.net;

import bgu.spl.net.BaseCommand.Command;
import bgu.spl.net.Commands.LogoutRequest;
import bgu.spl.net.api.MessagingProtocol;
import bgu.spl.net.api.User;


public class BGRS_Protocol implements MessagingProtocol<Command> {
    private boolean shouldTerminate = false;
    private final User user = new User();


    @Override
    public Command process (Command msg) {
        if (msg instanceof LogoutRequest)
            shouldTerminate = true;

        return msg.act(user);
    }

    @Override
    public boolean shouldTerminate() {
        return shouldTerminate;
    }
}
