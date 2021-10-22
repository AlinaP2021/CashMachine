package command;

import exception.InterruptOperationException;
import main.CashMachine;
import main.ConsoleHelper;
import main.CurrencyManipulator;
import main.CurrencyManipulatorFactory;

import java.util.Locale;
import java.util.ResourceBundle;

class DepositCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit", new Locale("en"));

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String code = ConsoleHelper.askCurrencyCode();
        String[] twoDigits = ConsoleHelper.getValidTwoDigits(code);
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);
        int denomination = Integer.parseInt(twoDigits[0]);
        int amount = Integer.parseInt(twoDigits[1]);
        manipulator.addAmount(denomination, amount);
        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), denomination * amount, manipulator.getCurrencyCode()));
    }
}
