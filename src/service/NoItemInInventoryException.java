package service;

public class NoItemInInventoryException extends Exception {
    public NoItemInInventoryException(String message) {
        super(message);
    }

}