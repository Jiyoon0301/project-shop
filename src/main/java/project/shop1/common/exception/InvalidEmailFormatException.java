package project.shop1.common.exception;

public class InvalidEmailFormatException extends Exception{

    public InvalidEmailFormatException() {
        super();
    }

    public InvalidEmailFormatException(String message) {
        super(message);
    }

    public InvalidEmailFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEmailFormatException(Throwable cause) {
        super(cause);
    }
}
