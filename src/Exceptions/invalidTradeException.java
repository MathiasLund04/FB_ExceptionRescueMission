package Exceptions;

public class invalidTradeException extends RuntimeException {
    public invalidTradeException(String message) {
        super(message);
    }
}
