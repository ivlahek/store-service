package hr.ivlahek.sample.store.exception.messages;

public enum ExceptionMessage implements ExceptionLogable {

    INTERNAL_ERROR(-1, "Internal error"),
    PRODUCT_SKU_ALREADY_EXISTS(1, "Product with the provided SKU already exists!"),
    PRODUCT_DOES_NOT_EXIST(2, "Product with the given id does not exist!"),
    PRODUCT_CART_EMPTY(3, "Product list in the placed order is empty!");
    private int errorCode;

    private String message;

    ExceptionMessage(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
