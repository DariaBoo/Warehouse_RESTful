package ua.foxminded.warehouse.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.warehouse.service.entities.Item;
import ua.foxminded.warehouse.service.entities.StockBalance;

/**
 * The StockBalanceDAO interface provides access to the database for performing
 * CRUD operations on the StockBalance entity.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Repository
public interface StockBalanceDAO extends JpaRepository<StockBalance, Integer> {

    /**
     * The method returns a current stock balance of a specific item
     * 
     * @param item
     * @return
     */
    Optional<StockBalance> findTopByItemOrderByDateDesc(Item item);

    /**
     * The method returns a stock balance of all items for a specific date ordered
     * by item id
     * 
     * @param date
     * @return - an optional list of StockBalance
     */
    Optional<List<StockBalance>> findByDateOrderByItemId(LocalDate date);

    /**
     * The method returns the stock balance of a specific item on a specific date
     * 
     * @param date
     * @param item
     * @return - optional StockBalance
     */
    Optional<StockBalance> findByDateAndItem(LocalDate date, Item item);
}
