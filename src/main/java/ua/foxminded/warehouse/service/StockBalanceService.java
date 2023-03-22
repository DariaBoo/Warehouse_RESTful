package ua.foxminded.warehouse.service;

import java.time.LocalDate;
import java.util.List;

import ua.foxminded.warehouse.service.entities.Item;
import ua.foxminded.warehouse.service.entities.StockBalance;
import ua.foxminded.warehouse.service.entities.Transaction;

/**
 * Interface for managing stock balances of a particular item in the warehouse.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
public interface StockBalanceService {
    
    /**
     * Finds a stock balance by ID.
     * 
     * @param id the ID of the stock balance to find
     * @return the stock balance with the given ID
     * @throws IllegalArgumentException if no stock balance with the specified ID is found.
     */
    StockBalance findById(int id);
    
    /**
     * Finds the current stock balance for an item.
     * 
     * @param item the item to find the current balance for
     * @return the current stock balance for the given item
     */
    StockBalance getTopBalance(Item item);
    
    /**
     * Finds all stock balances for a given date.
     * 
     * @param date the date to find stock balances for
     * @return a list of all stock balances for the given date, or an empty list if none found
     */
    List<StockBalance> findBalanceByDate(LocalDate date);
    
    /**
     * Updates a stock balance based on a transaction.
     * 
     * @param transaction the transaction to update the stock balance for
     */
    void topUpBalance(Transaction transaction);
    
    /**
     * Checks if a transaction can be completed with the current stock balances.
     * 
     * @param transaction the transaction to check
     * @return true if the transaction can be completed, false otherwise
     */
    boolean hasSufficientStock(Transaction transaction);
    
    /**
     * Finds a stock balance for a specific item on a specific date.
     * 
     * @param date the date to find the stock balance for
     * @param item the item to find the stock balance for
     * @return the stock balance for the given item on the given date
     */
    StockBalance findByDateAndItem(LocalDate date, Item item);
}
