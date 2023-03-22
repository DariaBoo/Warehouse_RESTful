package ua.foxminded.warehouse.service;

import java.time.LocalDate;
import java.util.List;

import ua.foxminded.warehouse.service.dto.Report;

/**
 * This interface defines the methods for generating reports related to the warehouse inventory.
 * The implementing class should provide functionality to retrieve a report on a specific item
 * for a certain period of time, and also a report on all items for a certain period of time.
 * 
 * @author Bohush Darya
 * @version 1.0
 */
public interface ReportService {

    /**
     * Retrieves a report on a specific item for a certain period of time.
     * 
     * @param itemId - the id of the item to retrieve the report for
     * @param dateFrom - start date (inclusive) of the period to retrieve the report for
     * @param dateTo - end date (inclusive) of the item to retrieve the report for
     * @return - a Report object containing the requested data
     */
    Report getItemReport(long itemId, LocalDate dateFrom, LocalDate dateTo);
    
    /**
     * Retrieves a report on all items for a certain period of time.
     * 
     * @param dateFrom - start date (inclusive) of the period to retrieve the report for
     * @param dateTo - end date (inclusive) of the period to retrieve the report for
     * @return - a list of Report objects containing the requested data
     */
    List<Report> getReportForAllItems(LocalDate dateFrom, LocalDate dateTo);
}
