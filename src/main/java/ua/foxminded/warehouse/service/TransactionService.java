package ua.foxminded.warehouse.service;

import java.time.LocalDate;
import java.util.List;

import ua.foxminded.warehouse.service.entities.Transaction;
import ua.foxminded.warehouse.service.exceptions.EntityServiceException;

/**
 * This interface provides methods for managing transactions.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
public interface TransactionService {

    /**
     * Creates a new transaction.
     * 
     * @param transaction The transaction to be created.
     * @return True if the transaction was created successfully, false otherwise.
     * @throws IllegalArgumentException if not enough items available for transaction.
     * @throws EntityServiceException If an error occurs while creating the transaction.
     */
    boolean create(Transaction transaction) throws EntityServiceException;

    /**
     * Updates an existing transaction.
     * 
     * @param transaction The transaction to be updated.
     */
    void update(Transaction transaction);

    /**
     * Removes a transaction.
     * 
     * @param id The ID of the transaction to be removed.
     */
    void remove(long id);

    /**
     * Finds transactions by date and item.
     * 
     * @param date The date of the transactions to be found.
     * @param itemId The ID of the item of the transactions to be found.
     * @return A list of transactions.
     * @throws IllegalArgumentException
     */
    List<Transaction> findByDateAndItem(LocalDate date, int itemId);

    /**
     * Finds transactions by date.
     * 
     * @param date The date of the transactions to be found.
     * @return A list of transactions.
     * @throws IllegalArgumentException
     */
    List<Transaction> findByDate(LocalDate date);

    /**
     * Finds a transaction by ID.
     * 
     * @param id The ID of the transaction to be found.
     * @return The transaction with the given ID
     * @throws IllegalArgumentException if no transaction with the specified ID is found.
     */
    Transaction findById(long id);
}
