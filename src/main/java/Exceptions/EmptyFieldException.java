package Exceptions;

public class EmptyFieldException extends ValidationException {
    public EmptyFieldException(String fieldName) {
        super(fieldName, "", "Pole " + fieldName + " nie może być puste");
    }
}