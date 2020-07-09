package Exceptions;

public class IllegalInputFormatException extends Exception{
    public IllegalInputFormatException() {
        super();
        superMessage = false;
    }
    public IllegalInputFormatException(String message) {
        super(message);
        superMessage = true;
    }
    @Override
    public String getMessage() {
        if (superMessage)
            return super.getMessage();
        else
            return "Wprowadzona wartość zawiera niedozwolone znaki!";
    }
    private final boolean superMessage;
}
