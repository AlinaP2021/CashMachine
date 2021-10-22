package command;

import exception.InterruptOperationException;
import exception.NotEnoughMoneyException;
import main.CashMachine;
import main.ConsoleHelper;
import main.CurrencyManipulator;
import main.CurrencyManipulatorFactory;

import java.util.*;
import java.util.stream.Collectors;

class WithdrawCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw", new Locale("en"));

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String code = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);

        while (true) {
            try {
                ConsoleHelper.writeMessage(res.getString("specify.amount"));
                String answer = ConsoleHelper.readString();
                if (answer == null)
                    ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                else if (answer.matches("\\d+")) {
                    int amount = Integer.parseInt(answer);
                    if (manipulator.isAmountAvailable(amount)) {
                        Map<Integer, Integer> map = manipulator.withdrawAmount(amount);
                        List<Integer> keys = map.keySet().stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());
                        for (Integer key : keys) {
                            ConsoleHelper.writeMessage("\t" + key + " - " + map.get(key));
                        }
                        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), amount, manipulator.getCurrencyCode()));
                        break;
                    } else ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                } else ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            }
        }

    }
}
