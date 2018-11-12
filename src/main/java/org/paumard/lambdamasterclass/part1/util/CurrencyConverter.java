package org.paumard.lambdamasterclass.part1.util;

import java.time.LocalDate;
import java.util.Map;

public interface CurrencyConverter {

    static WithDate of(LocalDate date) {
        return new WithDate(CONVERTERS, date);
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

    final class WithDate {
        private final Map<String, Map<String, CurrencyConverter>> map;

        WithDate(Map<LocalDate, Map<String, Map<String, CurrencyConverter>>> map, LocalDate date) {
            this.map = map.get(date);
        }

        public WithDateAndFrom from(String from) {
            return new WithDateAndFrom(map, from);
        }
    }

    final class WithDateAndFrom {
        private final Map<String, CurrencyConverter> map;

        WithDateAndFrom(Map<String, Map<String, CurrencyConverter>> map, String from) {
            this.map = map.get(from);
        }

        public CurrencyConverter to(String to) {
            return map.get(to);
        }
    }

}
