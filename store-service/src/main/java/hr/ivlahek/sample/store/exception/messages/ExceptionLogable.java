package hr.ivlahek.sample.store.exception.messages;

public interface ExceptionLogable {
    int getErrorCode();

    String getMessage();
}
