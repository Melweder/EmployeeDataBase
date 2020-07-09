package Exceptions;

public class DataLengthMismatch extends Exception{
    @Override
    public String getMessage() {
        return "Błędny rozmiar wprowadzonych tablic";
    }
}
