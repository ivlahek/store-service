package hr.ivlahek.sample.store.exception;


import hr.ivlahek.sample.store.exception.messages.ExceptionLogable;

public class NotFoundException extends AppException {
    public NotFoundException(ExceptionLogable exceptionMessage) {
        super(exceptionMessage.getErrorCode(), 404, exceptionMessage.getMessage());
    }
}
