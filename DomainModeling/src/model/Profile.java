package model;

public interface Profile {

    Model create() throws ValidationException;





    class ValidationException extends Exception {
    }
}
