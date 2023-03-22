package ua.foxminded.warehouse.controller.urls;

/**
 * 
 * The RestUrl class defines static String constants that represent various
 * RESTful endpoint URLs for different controllers in an API.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
public class RestUrl {

    // CategoryController
    public static final String CREATE_CATEGORY = "/categories/new";
    public static final String UPDATE_CATEGORY = "/categories/update/{id}";
    public static final String REMOVE_CATEGORY = "/categories/remove/{id}";
    public static final String FIND_CATEGORY_BY_ID = "/categories/findById/{id}";
    public static final String FIND_ALL_CATEGORIES = "/categories/findAll";

    // ItemController
    public static final String CREATE_ITEM = "/items/new";
    public static final String UPDATE_ITEM = "/items/update/{id}";
    public static final String REMOVE_ITEM = "/items/remove/{id}";
    public static final String FIND_ITEM_BY_ID = "/items/findById/{id}";
    public static final String FIND_ALL_ITEMS = "/items/findAll";

    // PartnerController
    public static final String CREATE_PARTNER = "/partners/new";
    public static final String UPDATE_PARTNER = "/partners/update/{id}";
    public static final String REMOVE_PARTNER = "/partners/remove/{id}";
    public static final String FIND_PARTNER_BY_ID = "/partners/findById/{id}";
    public static final String FIND_ALL_CUSTOMERS = "/partners/findAllCustomers";
    public static final String FIND_ALL_SUPPLIERS = "/partners/findAllSuppliers";

    // AddressController
    public static final String CREATE_ADDRESS = "/addresses/new";
    public static final String UPDATE_ADDRESS = "/addresses/update/{id}";
    public static final String REMOVE_ADDRESS = "/addresses/remove/{id}";
    public static final String FIND_ADDRESS_BY_PARTNER_ID = "/addresses/findByPartnerId/{id}";

    // ReportController
    public static final String GET_ITEM_REPORT = "/reports/getItemReport";
    public static final String GET_TOTAL_REPORT = "/reports/getReportForAllItems";

    // StockBalanceController
    public static final String GET_BALANCE_BY_DATE = "/stocks/findBalanceByDate";
    public static final String GET_BALANCE_BY_ID = "/stocks/findBalanceById/{id}";
    public static final String GET_CURRENT_BALANCE = "/stocks/findCurrentBalance";

    // TransactionController
    public static final String CREATE_TRANSACTION = "/transactions/new";
    public static final String UPDATE_TRANSACTION = "/transactions/update/{id}";
    public static final String REMOVE_TRANSACTION = "/transactions/remove/{id}";
    public static final String FIND_TRANSACTION_BY_ID = "/transactions/findById/{id}";
    public static final String FIND_TRANSACTION_BY_DATE_AND_ITEM = "/transactions/findByDateAndItem";
    public static final String FIND_TRANSACTION_BY_DATE = "/transactions/findByDate";

    private RestUrl() {

    }
}
