package org.paumard.lambdamasterclass.part1.util;

import java.time.LocalDate;
import java.util.Map;

public interface CurrencyConverter {

    static WithDate of(LocalDate date) {
        return from -> to -> CONVERTERS.get(date).get(from).get(to);
    }

    double convert(double amount);

    Map<LocalDate, Map<String, Map<String, CurrencyConverter>>> CONVERTERS = Map.of(
            LocalDate.of(2018, 11, 5),
            Map.of(
                    "EUR", Map.of("GBP", (it) -> it * 0.87749d),
                    "NOK", Map.of("EUR", (it) -> it * 0.10507d),
                    "CHF", Map.of("CAD", (it) -> it * 1.30665d)
            )
    );

    @FunctionalInterface
    interface WithDate {
        WithDateAndFrom from(String from);
    }

    @FunctionalInterface
    interface WithDateAndFrom {
        CurrencyConverter to(String to);
    }

}
