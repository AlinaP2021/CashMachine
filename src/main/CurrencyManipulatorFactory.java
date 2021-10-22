package main;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CurrencyManipulatorFactory {
    private static Map<String, CurrencyManipulator> map = new HashMap<>();

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode) {
        for (String key : map.keySet()) {
            if (key.equals(currencyCode.toLowerCase())) return map.get(currencyCode.toLowerCase());
        }
        CurrencyManipulator manipulator = new CurrencyManipulator(currencyCode);
        map.put(currencyCode.toLowerCase(), manipulator);
        return manipulator;
    }

    private CurrencyManipulatorFactory() {}

    public static Collection<CurrencyManipulator> getAllCurrencyManipulators() {
        return map.values();
    }
}
