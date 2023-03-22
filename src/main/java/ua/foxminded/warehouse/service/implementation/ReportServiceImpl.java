package ua.foxminded.warehouse.service.implementation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.warehouse.dao.ReportDAO;
import ua.foxminded.warehouse.service.ReportService;
import ua.foxminded.warehouse.service.dto.Report;

/**
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportDAO reportDao;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Report getItemReport(long itemId, LocalDate dateFrom, LocalDate dateTo) {
        Report report = reportDao.getItemReport(itemId, dateFrom, dateTo)
                .orElseThrow(() -> new IllegalArgumentException("Unable to retrieve report for item " + itemId
                        + " between " + dateFrom + " and " + dateTo + ". No open stock found for item."));
        log.info("Retrieved item report for item {} between {} and {}", itemId, dateFrom, dateTo);
        return report;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Report> getReportForAllItems(LocalDate dateFrom, LocalDate dateTo) {
        List<Report> reportList = reportDao.getTotalReport(dateFrom, dateTo)
                .orElseThrow(() -> new IllegalArgumentException("No report found for the given date range"));
        log.info("Retrieved report for all items between {} and {}", dateFrom, dateTo);
        return reportList;
    }
}
