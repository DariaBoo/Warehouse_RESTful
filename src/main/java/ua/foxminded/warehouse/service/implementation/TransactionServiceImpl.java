package ua.foxminded.warehouse.service.implementation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.warehouse.dao.TransactionDAO;
import ua.foxminded.warehouse.service.StockBalanceService;
import ua.foxminded.warehouse.service.TransactionService;
import ua.foxminded.warehouse.service.entities.Transaction;
import ua.foxminded.warehouse.service.exceptions.EntityNotFoundException;
import ua.foxminded.warehouse.service.exceptions.EntityServiceException;

/**
 * Service implementation class for managing transactions. This class implements
 * the {@link TransactionService} interface and provides methods to create,
 * update, remove, and retrieve transactions from the database.
 * 
 * @author Bohush Darya
 * @version 1.0
 */
@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionDAO transactionDao;
    @Autowired
    private StockBalanceService stockBalanceService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public boolean create(Transaction transaction) throws EntityServiceException {
        try {
            if (!transaction.getType().isIncome() && !stockBalanceService.hasSufficientStock(transaction)) {
                String errorMessage = "Not enough items available for transaction " + transaction.getId()
                        + ". Transaction cannot proceed.";
                log.error(errorMessage);
                throw new IllegalArgumentException(errorMessage);
            }
            transactionDao.save(transaction);
            stockBalanceService.topUpBalance(transaction);
            log.info("Transaction created successfully. ID: {}", transaction.getId());
            return true;
        } catch (DataAccessException e) {
            log.error("An error occurred while creating transaction " + transaction.getId(), e);
            String errorMessage = "An error occurred while creating transaction " + transaction.getId()
                    + ". Please try again later.";
            throw new EntityServiceException(errorMessage, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void update(Transaction transaction) {
        transactionDao.save(transaction);
        log.info("Transaction updated successfully. ID: {}", transaction.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void remove(long id) {
        if(transactionDao.existsById(id)) {
        transactionDao.deleteById(id);
        log.info("Transaction removed. ID: {}", id);
        } else {
            log.error("Failed to remove transaction. ID: %s. Entity not found.", id);
            throw new EntityNotFoundException("Transaction not found with ID: " + id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Transaction findById(long id) {
        Transaction transaction = transactionDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No transaction with id " + id));
        log.info("Transaction was found successfully! ID: {}", id);
        return transaction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findByDateAndItem(LocalDate date, int itemId) {
        List<Transaction> transactions = transactionDao.findAllByDateAndItemId(date, itemId)
                .orElseThrow(() -> new IllegalArgumentException("Error occurred"));
        log.info("Transactions found by date and item. Date: {}, ItemID: {}", date, itemId);
        return transactions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findByDate(LocalDate date) {
        List<Transaction> transactions = transactionDao.findAllByDate(date)
                .orElseThrow(() -> new IllegalArgumentException("Error occurred"));
        log.info("Transactions found by date. Date: {}", date);
        return transactions;
    }
}
