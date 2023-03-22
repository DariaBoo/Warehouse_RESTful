package ua.foxminded.warehouse.service.enums;

/**
 * An enumeration representing the types of transactions that can occur in the
 * system. 
 * Possible transaction types are: 
 * PURCHASE: Represents a purchase transaction. 
 * SALES: Represents a sales transaction. 
 * RETURN: Represents a return transaction. 
 * TRANSFER: Represents a transfer transaction.
 * 
 * Each transaction type has a corresponding boolean variable to indicate whether
 * the transaction represents an income or an expense.
 * 
 * This enumeration is used to categorize transactions and generate financial reports
 * based on income and expenses.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
public enum TransactionType {

    PURCHASE(true), 
    SALES(false), 
    RETURN(true), 
    TRANSFER_FROM(true),
    TRANSFER_TO(false);
    
    private final boolean isIncome;
    
    TransactionType(boolean isIncome){
        this.isIncome = isIncome;
    }
    
    public boolean isIncome() {
        return this.isIncome;
    }
}
