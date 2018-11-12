package org.paumard.lambdamasterclass.part1.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.paumard.lambdamasterclass.part1.util.CurrencyConverter.CurrencyMapHolder.MAP;

public interface CurrencyConverter {

    class CurrencyMapHolder {
        static Map<String, Double> MAP;

        static {
            try {
                MAP = Files.lines(Paths.get("./data/currency.txt"))
                        .skip(1)
                        .collect(toMap(
                                line -> line.split("=")[0],
                                line -> Double.valueOf(line.split("=")[1])
                        ));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static WithDate of(LocalDate date) {
        return from -> to -> amount -> amount * MAP.get(to) / MAP.get(from);
    }

    double convert(double amount);

    @FunctionalInterface
    interface WithDate {
        WithDateAndFrom from(String from);
    }

    @FunctionalInterface
    interface WithDateAndFrom {
        CurrencyConverter to(String to);
    }

}
