package main;

import exception.NotEnoughMoneyException;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CurrencyManipulator {
    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if (!denominations.containsKey(denomination)) denominations.put(denomination, count);
        else denominations.put(denomination, denominations.get(denomination) + count);
    }

    public int getTotalAmount() {
        int totalAmount = 0;
        for (Map.Entry<Integer, Integer> entry : denominations.entrySet()) {
            totalAmount += entry.getKey() * entry.getValue();
        }
        return totalAmount;
    }

    public boolean hasMoney() {
        if (getTotalAmount() > 0) return true;
        return false;
    }

    public boolean isAmountAvailable(int expectedAmount) {
        if (getTotalAmount() >= expectedAmount) return true;
        return false;
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> keysList = denominations.keySet().stream().sorted(Collections.reverseOrder()).collect(Collectors.toList());

        for (Integer key : keysList) {
            if (expectedAmount > key && denominations.get(key) != 0) {
                map = withdrawAmount(expectedAmount - key);
                if (map.size() != 0) {
                    if (map.containsKey(key)) map.put(key, map.get(key) + 1);
                    else map.put(key, 1);
                    denominations.put(key, denominations.get(key) - 1);
                    return map;
                }
            } else if (expectedAmount == key && denominations.get(key) != 0) {
                map.put(key, 1);
                denominations.put(key, denominations.get(key) - 1);
                return map;
            }
        }
        if (map.size() == 0) throw new NotEnoughMoneyException();
        return map;
    }
}
