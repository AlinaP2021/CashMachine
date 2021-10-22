package main;

import command.CommandExecutor;
import exception.InterruptOperationException;

public class CashMachine {
    public static final String RESOURCE_PATH = "resources.";

    public static void main(String[] args) {
        try {
            Operation operation = Operation.LOGIN;
            CommandExecutor.execute(operation);
            do {
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
            } while (operation != Operation.EXIT);
        } catch (InterruptOperationException e) {
            ConsoleHelper.printExitMessage();
        }
    }
}
