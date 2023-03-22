package ua.foxminded.warehouse.service.implementation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.warehouse.dao.StockBalanceDAO;
import ua.foxminded.warehouse.service.StockBalanceService;
import ua.foxminded.warehouse.service.entities.Item;
import ua.foxminded.warehouse.service.entities.StockBalance;
import ua.foxminded.warehouse.service.entities.Transaction;

/**
 * Service implementation class for managing stock_balance objects. This class
 * implements the {@link StockBalanceService} interface and provides methods to
 * retrieve and update stock_balance from the database.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Slf4j
@Service
@Transactional
public class StockBalanceServiceImpl implements StockBalanceService {

    @Autowired
    private StockBalanceDAO stockBalanceDao;

    private final ZoneId zoneId = ZoneId.of("Europe/Warsaw");
    private final LocalDate today = LocalDate.now(zoneId);

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public StockBalance findById(int id) {
        StockBalance stockBalance = stockBalanceDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find StockBalance with id: " + id));
        log.info("Found a stock balance by ID. ID: {}", id);
        return stockBalance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<StockBalance> findBalanceByDate(LocalDate date) {
        List<StockBalance> stockBalances = stockBalanceDao.findByDateOrderByItemId(date)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find stock balances for date: " + date));
        log.info("Found a list of stock balances by date. DATE: {}", date);
        return stockBalances;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public StockBalance getTopBalance(Item item) {
        StockBalance stockBalance = stockBalanceDao.findTopByItemOrderByDateDesc(item).orElseThrow(
                () -> new IllegalArgumentException("No stock balance found for item with ID: " + item.getId()));
        log.info("Found current stock balance for item ID: {}", item.getId());
        return stockBalance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void topUpBalance(Transaction transaction) {
        BigDecimal transactionQuantity = transaction.getQuantity();
        Item item = transaction.getItem();
        try {
            StockBalance currentBalance = getTopBalance(item);
            if (currentBalance.getDate().isEqual(today)) {
                doTopUpBalance(currentBalance, transactionQuantity);
            } else {
                updateBalance(currentBalance, transactionQuantity);
            }
        } catch (IllegalArgumentException e) {
            createCurrentBalance(item, transactionQuantity);
        }
    }

    private void doTopUpBalance(StockBalance currentBalance, BigDecimal transactionQuantity) {
        currentBalance.setStockRemainder(currentBalance.getStockRemainder().add(transactionQuantity));
        stockBalanceDao.save(currentBalance);
        log.info("Top up - current balance: {} | transaction quantity: {}", currentBalance, transactionQuantity);
    }

    private void updateBalance(StockBalance currentBalance, BigDecimal transactionQuantity) {
        currentBalance.setDate(today);
        currentBalance.setStockRemainder(currentBalance.getStockRemainder().add(transactionQuantity));
        stockBalanceDao.save(currentBalance);
        log.info("Updated - current balance: {} | transaction quantity: {}", currentBalance, transactionQuantity);
    }

    private void createCurrentBalance(Item item, BigDecimal transactionQuantity) {
        StockBalance newBalance = StockBalance.builder().date(today).item(item).stockRemainder(transactionQuantity)
                .build();
        stockBalanceDao.save(newBalance);
        log.info("Created new balance for ITEM: {}", item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasSufficientStock(Transaction transaction) {
        BigDecimal currentQuantity = getTopBalance(transaction.getItem()).getStockRemainder();
        BigDecimal transactionQuantity = transaction.getQuantity();
        boolean isSufficient = currentQuantity.compareTo(transactionQuantity.abs()) >= 0;
        log.info("Checking stock sufficiency for transaction: {} - Current quantity: {} - Required quantity: {} - Result: {}",
                transaction.getId(), currentQuantity, transactionQuantity, isSufficient);
        return isSufficient;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public StockBalance findByDateAndItem(LocalDate date, Item item) {
        StockBalance stockBalance = stockBalanceDao.findByDateAndItem(date, item)
                .orElseThrow(() -> new IllegalArgumentException("No stock balance found for item with ID: " + item.getId() + " on DATE: " + date));
        log.info("Found stock balance for item {} on date {}: {}", item.getId(), date, stockBalance.getId());
        return stockBalance;
    }
}
