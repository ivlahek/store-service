package hr.ivlahek.sample.store.exception;

import hr.ivlahek.sample.store.exception.messages.ExceptionLogable;

public class ConflictErrorException extends AppException {

    public ConflictErrorException(ExceptionLogable exceptionMessage) {
        super(exceptionMessage.getErrorCode(), 409, exceptionMessage.getMessage());
    }
}
