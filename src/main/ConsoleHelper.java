package main;

import exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConsoleHelper {
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common", new Locale("en"));

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        String string = "";
        try {
            string = bis.readLine();
        } catch (IOException e) {}
        if (string.equalsIgnoreCase("exit")) {
            throw new InterruptOperationException();
        }
        return string;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        writeMessage(res.getString("choose.currency.code"));
        String code = readString();
        if (!code.matches("[a-zA-Z]{3}")) {
            writeMessage(res.getString("invalid.data"));
            return askCurrencyCode();
        }
        return code.toUpperCase();
    }

    public static String[] getValidTwoDigits(String code) throws InterruptOperationException {
        writeMessage(String.format(res.getString("choose.denomination.and.count.format"), code));
        String result = readString();
        if (!result.matches("\\d+\\s\\d+")) {
            writeMessage(res.getString("invalid.data"));
            return getValidTwoDigits(code);
        }
        return new String[] {result.split(" ")[0], result.split(" ")[1]};
    }

    public static Operation askOperation() throws InterruptOperationException {
        writeMessage(res.getString("choose.operation"));
        writeMessage("1 - INFO, 2 - DEPOSIT, 3 - WITHDRAW, 4 - EXIT");
        Operation operation;
        try {
            operation = Operation.getAllowableOperationByOrdinal(Integer.parseInt(readString()));
        } catch (IllegalArgumentException e) {
            return askOperation();
        }
        return operation;
    }

    public static void printExitMessage() {
        writeMessage(res.getString("the.end"));
    }
}
