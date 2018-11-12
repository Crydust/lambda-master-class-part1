package org.paumard.lambdamasterclass.part1.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static java.util.Collections.unmodifiableList;

public interface Validator<T> {

    static <U> Validator<U> firstValidate(Predicate<U> predicate, String message) {
        return new ValidatorImpl<>(predicate, message);
    }

    Validator<T> thenValidate(Predicate<T> predicate, String message);

    Supplier<T> validate(T person);

    class Validation<U> {
        private final Predicate<U> predicate;
        private final String message;

        Validation(Predicate<U> predicate, String message) {
            this.predicate = predicate;
            this.message = message;
        }

        private void validate(U object) {
            if (predicate.test(object)) {
                throw new ValidationException(message);
            }
        }
    }

    class ValidatorImpl<U> implements Validator<U> {
        private final List<Validation<U>> validations;

        ValidatorImpl(Predicate<U> predicate, String message) {
            validations = List.of(new Validation<>(predicate, message));
        }

        private ValidatorImpl(List<Validation<U>> validations) {
            this.validations = unmodifiableList(new ArrayList<>(validations));
        }

        @Override
        public Validator<U> thenValidate(Predicate<U> predicate, String message) {
            final ArrayList<Validation<U>> list = new ArrayList<>(validations);
            list.add(new Validation<>(predicate, message));
            return new ValidatorImpl<>(list);
        }

        @Override
        public Supplier<U> validate(U object) {
            for (Validation<U> validation : validations) {
                validation.validate(object);
            }
            return () -> object;
        }

    }

    class ValidationException extends RuntimeException {
        public ValidationException() {
            super();
        }

        ValidationException(String message) {
            super(message);
        }

        public ValidationException(String message, Throwable cause) {
            super(message, cause);
        }

        public ValidationException(Throwable cause) {
            super(cause);
        }
    }
}
