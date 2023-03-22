package ua.foxminded.warehouse.controller.messages;

/**
 * The ResponseMessage class defines static String constants that represent
 * various responses that can be returned by an API. These messages can be used
 * to indicate the status of a particular operation, such as creating, updating,
 * or removing a category, address, transaction, item, or partner.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
public class ResponseMessage {

    public static final String MSG_SUCCESS = "success";
    public static final String MSG_ERROR = "ERROR";

    public static final String CATEGORY_CREATED = "Category was created successfully!";
    public static final String CATEGORY_UPDATED = "Category was updated successfully!";
    public static final String CATEGORY_REMOVED = "Category removed successfully!";

    public static final String ADDRESS_CREATED = "Address was created successfully!";
    public static final String ADDRESS_UPDATED = "Address was updated successfully!";
    public static final String ADDRESS_REMOVED = "Address removed successfully!";

    public static final String TRANSACTION_CREATED = "Transaction was created successfully!";
    public static final String TRANSACTION_UPDATED = "Transaction was updated successfully!";
    public static final String TRANSACTION_REMOVED = "Transaction removed successfully!";

    public static final String ITEM_CREATED = "Item was created successfully!";
    public static final String ITEM_UPDATED = "Item was updated successfully!";
    public static final String ITEM_REMOVED = "Item removed successfully!";

    public static final String PARTNER_CREATED = "Partner was created successfully!";
    public static final String PARTNER_UPDATED = "Partner was updated successfully!";
    public static final String PARTNER_REMOVED = "Partner removed successfully!";

    private ResponseMessage() {

    }
}
