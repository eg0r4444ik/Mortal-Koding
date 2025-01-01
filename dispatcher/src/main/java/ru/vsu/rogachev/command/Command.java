package ru.vsu.rogachev.command;

import ru.vsu.rogachev.entities.User;

public interface Command {

    void execute(User user);

}
