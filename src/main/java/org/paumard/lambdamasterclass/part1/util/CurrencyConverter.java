package org.paumard.lambdamasterclass.part1.util;

import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;

public interface CurrencyConverter {

    static CurrencyConverterFinder of(LocalDate date) {
        return new CurrencyConverterFinder(date);
    }

    double convert(double amount);


    Map<Id, CurrencyConverter> converters = Map.of(
            new Id("EUR", "GBP"), (it) -> it * 0.87749d,
            new Id("NOK", "EUR"), (it) -> it * 0.10507d,
            new Id("CHF", "CAD"), (it) -> it * 1.30665d
    );

    final class CurrencyConverterFinder {

        private final LocalDate date;
        private String from;
        private String to;

        CurrencyConverterFinder(LocalDate date) {
            this.date = date;
        }

        public CurrencyConverterFinder from(String from) {
            this.from = from;
            return this;
        }

        public CurrencyConverter to(String to) {
            this.to = to;
            return converters.get(new Id(from, to));
        }
    }

    final class Id {

        private final String from;
        private final String to;

        Id(String from, String to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Id that = (Id) o;
            return Objects.equals(from, that.from) &&
                    Objects.equals(to, that.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }
    }

}
