package io.spring.sample.greetingservice;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }
}
