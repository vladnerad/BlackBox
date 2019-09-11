package command;

import exception.InterruptOperationException;

public interface Command {
    void execute() throws InterruptOperationException;
}