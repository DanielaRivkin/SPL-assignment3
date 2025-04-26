package bgu.spl.net.BaseCommand;

import bgu.spl.net.api.User;

public interface Command {
    Command act(User user);
}
