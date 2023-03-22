package ua.foxminded.warehouse.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import ua.foxminded.warehouse.controller.messages.ResponseMessage;
import ua.foxminded.warehouse.controller.swagger_response.SwaggerResponse;
import ua.foxminded.warehouse.controller.urls.RestUrl;
import ua.foxminded.warehouse.service.ReportService;
import ua.foxminded.warehouse.service.dto.FinalResponse;
import ua.foxminded.warehouse.service.dto.Report;

/**
 * REST controller for retrieving reports
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Slf4j
@RestController
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * Returns a report for a specific product within a given period of time.
     * 
     * @param dateFrom - start date (included)
     * @param dateTo   - end date (included)
     * @param itemId   - the ID of the item to generate the report for
     * @return - a ResponseEntity containing the generated report
     */
    @Operation(summary = "Get a report by item and period of dates", description = "Returns a report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.FOUND_CODE, description = SwaggerResponse.FOUND_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.NOT_FOUND_CODE, description = SwaggerResponse.NOT_FOUND_DESCRIPTION) })
    @GetMapping(value = RestUrl.GET_ITEM_REPORT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinalResponse<Report>> getItemReport(@RequestParam String dateFrom,
            @RequestParam String dateTo, @RequestParam Integer itemId) {
        FinalResponse<Report> result = new FinalResponse<>();
        try {
            log.info("Generating report for item {} between dates {} and {}", itemId, dateFrom, dateTo);            
            Report report = reportService.getItemReport(itemId, LocalDate.parse(dateFrom), LocalDate.parse(dateTo));
            result.setData(report);
            result.setMessage(ResponseMessage.MSG_SUCCESS);
        } catch (IllegalArgumentException e) {
            String message = ResponseMessage.MSG_ERROR + e.getMessage();
            result.setMessage(message);
            log.error(e.getLocalizedMessage());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Returns a report for all products within a given period of time.
     * 
     * @param dateFrom - start date (included)
     * @param dateTo - end date (included)
     * @return - a ResponseEntity containing the generated report
     */
    @Operation(summary = "List reports by period of dates", description = "Returns a list of reports")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.FOUND_CODE, description = SwaggerResponse.FOUND_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.NOT_FOUND_CODE, description = SwaggerResponse.NOT_FOUND_DESCRIPTION) })
    @GetMapping(value = RestUrl.GET_TOTAL_REPORT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinalResponse<Report>> getTotalReport(@RequestParam String dateFrom,
            @RequestParam String dateTo) {
        FinalResponse<Report> result = new FinalResponse<>();
        try {
            log.info("Generating report for all items between dates {} and {}", dateFrom, dateTo);
            List<Report> reports = reportService.getReportForAllItems(LocalDate.parse(dateFrom), LocalDate.parse(dateTo));
            result.setDataList(reports);
            result.setMessage(ResponseMessage.MSG_SUCCESS);
        } catch (IllegalArgumentException e) {
            String message = ResponseMessage.MSG_ERROR + e.getMessage();
            result.setMessage(message);
            log.error(e.getLocalizedMessage());
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
