package ua.foxminded.warehouse.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.foxminded.warehouse.service.dto.Report;

/**
 * This interface provides methods for generating reports based on the stock
 * balance and transactions data.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Repository
public interface ReportDAO extends JpaRepository<Report, Integer> {

    /**
     * The method returns a report on a specific item for a certain period of
     * time
     * 
     * @param itemId
     * @param dateFrom - start date (included)
     * @param dateTo   - end date (included)
     * @return - a report on a specific item for a certain period of time
     */
    @Query(value = "SELECT s.item_id, i.selling_price item_price, s.stock_remainder open_balance, r.stock_remainder close_balance,\n"
            + "sum(t.quantity) transactions_quantity, sum(t.unit_price) transactions_total_price FROM warehouse.stock_balance AS s \n"
            + "LEFT JOIN warehouse.stock_balance AS r ON s.item_id = r.item_id LEFT JOIN warehouse.transactions\n"
            + "AS t ON t.item_id = s.item_id LEFT JOIN warehouse.items i ON i.id = s.item_id WHERE s.item_id = ?1 AND s.date = ?2 AND r.date = ?3\n"
            + "AND t.date >= ?2 AND t.date <= ?3 GROUP BY s.item_id, i.selling_price, s.stock_remainder, r.stock_remainder;", nativeQuery = true)
    public Optional<Report> getItemReport(long itemId, LocalDate dateFrom, LocalDate dateTo);

    /**
     * The method returns a report on all items for a certain period of time
     * 
     * @param dateFrom - start date (included)
     * @param dateTo   - end date (included)
     * @return a report on all items for a certain period of time
     */
    @Query(value = "SELECT s.item_id, i.selling_price item_price, s.stock_remainder open_balance, r.stock_remainder close_balance,\n"
            + "sum(t.quantity) transactions_quantity, sum(t.unit_price) transactions_total_price FROM warehouse.stock_balance AS s \n"
            + "LEFT JOIN warehouse.items i ON i.id = s.item_id  LEFT JOIN warehouse.transactions\n"
            + "AS t ON t.item_id = s.item_id LEFT JOIN warehouse.stock_balance AS r ON s.item_id = r.item_id WHERE s.date = ?1 AND r.date = ?2\n"
            + "AND t.date >= ?1 AND t.date <= ?2 GROUP BY s.item_id, i.selling_price, s.stock_remainder, r.stock_remainder;", nativeQuery = true)
    public Optional<List<Report>> getTotalReport(LocalDate dateFrom, LocalDate dateTo);
}
