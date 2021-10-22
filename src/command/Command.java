package command;

import exception.InterruptOperationException;

interface Command {
    void execute() throws InterruptOperationException;
}
