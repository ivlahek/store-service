package hr.ivlahek.sample.store.client.validation;

public class ValidationMessages {
    public static final String PRODUCT_NAME_EMPTY = "Product name is empty!";
    public static final String PRODUCT_NAME_NULL = "Product name is null!";

    public static final String PRODUCT_DESCRIPTION_EMPTY = "Product description is empty!";
    public static final String PRODUCT_DESCRIPTION_NULL = "Product description is null!";
    public static final String PRODUCT_PRICE_NULL = "Product price is null!";
    public static final String PRODUCT_PRICE_NEGATIVE_OR_ZERO = "Product price must be positive!";

    public static final String ORDER_EMAIL_IS_NULL = "Email is null!";
    public static final String ORDER_EMAIL_IS_EMPTY = "Email is empty!";
    public static final String ORDER_NOT_A_VALID_EMAIL = "Not a valid email!";
    public static final String ORDER_ITEM_LIST_EMPTY = "Item list empty!";
    public static final String ORDER_ITEM_LIST_NULL = "Item list null!";
    public static final String DATE_FROM_NOT_FOUND = "Date from not found!";
    public static final String DATE_TO_NOT_FOUND = "Date to not found!";
    public static final String PRODUCT_ID_NULL = "Product id is null!";
    public static final String ORDER_ITEM_QUANTITY_NULL = "Order item quantity is null";
    public static final String ORDER_ITEM_QUANTITY_NEGATIVE_NUMBER = "Order item quantity must be positive number grater than zero!";
}
