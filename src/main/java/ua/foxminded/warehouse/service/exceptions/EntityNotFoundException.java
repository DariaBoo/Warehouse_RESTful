package ua.foxminded.warehouse.service.exceptions;

public class EntityNotFoundException extends RuntimeException {
    
    public EntityNotFoundException(String message) {
        super(message);
    }
    
    public EntityNotFoundException(String message, Exception e) {
        super(message, e);
    }

}
