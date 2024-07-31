package dev.ddash.reactivevalidation.exception;

public class CustomerNotFoundException extends RuntimeException {

    public static final String MESSAGE = "Customer [id=%d] not found";
    public CustomerNotFoundException(int id) {
        super(MESSAGE.formatted(id));
    }
}
