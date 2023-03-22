package ua.foxminded.warehouse.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.warehouse.service.entities.Transaction;

/**
 * The TransactionDAO interface provides access to the database for performing CRUD operations on the Transaction entity.
 *
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Repository
public interface TransactionDAO extends JpaRepository<Transaction, Long> {

    /**
     * The method returns a list of transactions for a specific item on a specific date
     * @param date
     * @param itemId
     * @return - optional list of Transaction
     */
    Optional<List<Transaction>> findAllByDateAndItemId(LocalDate date, int itemId);

    /**
     * The method returns a list of transactions for all items on a specific date
     * @param date
     * @return - optional list of Transaction
     */
    Optional<List<Transaction>> findAllByDate(LocalDate date);
}
