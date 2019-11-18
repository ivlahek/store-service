package hr.ivlahek.sample.store.exception;


import hr.ivlahek.sample.store.exception.messages.ExceptionLogable;

public class InternalServerErrorException extends AppException {

    public InternalServerErrorException(ExceptionLogable exceptionMessage) {
        super(exceptionMessage.getErrorCode(), 500, exceptionMessage.getMessage());
    }
}
