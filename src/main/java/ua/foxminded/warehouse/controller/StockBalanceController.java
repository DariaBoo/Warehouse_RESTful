package ua.foxminded.warehouse.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import ua.foxminded.warehouse.controller.messages.ResponseMessage;
import ua.foxminded.warehouse.controller.swagger_response.SwaggerResponse;
import ua.foxminded.warehouse.controller.urls.RestUrl;
import ua.foxminded.warehouse.service.StockBalanceService;
import ua.foxminded.warehouse.service.dto.FinalResponse;
import ua.foxminded.warehouse.service.entities.Item;
import ua.foxminded.warehouse.service.entities.StockBalance;

/**
 * REST controller for managing StockBalance objects.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Slf4j
@RestController
public class StockBalanceController {

    @Autowired
    private StockBalanceService stockService;

    /**
     * Retrieves a stock balance for a given ID.
     *
     * @param id the ID of the stock balance to retrieve
     * @return the stock balance with the given ID, wrapped in a ResponseEntity object
     */
    @Operation(summary = "Get a stock balance by id", description = "Returns a stock balance as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.FOUND_CODE, description = SwaggerResponse.FOUND_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.NOT_FOUND_CODE, description = SwaggerResponse.NOT_FOUND_DESCRIPTION) })
    @GetMapping(value = RestUrl.GET_BALANCE_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinalResponse<StockBalance>> findById(@PathVariable Integer id) {
        log.info("Received request to retrieve stock balances for id: {}", id);        
        FinalResponse<StockBalance> result = new FinalResponse<>();        
        try {
            StockBalance balance = stockService.findById(id);
            result.setData(balance);
            result.setMessage(ResponseMessage.MSG_SUCCESS);
        } catch (IllegalArgumentException e) {
            String message = ResponseMessage.MSG_ERROR + e.getMessage();
            result.setMessage(message);
            log.error("An error occurred while retrieving stock balance by id: {}", id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    /**
     * Retrieves a list of stock balances for a given date.
     *
     * @param date the date for which to retrieve the stock balances
     * @return the stock balances for the given date, wrapped in a ResponseEntity object
     */
    @Operation(summary = "List stock balances by date", description = "Returns a list of stock balances")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.FOUND_CODE, description = SwaggerResponse.FOUND_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.NOT_FOUND_CODE, description = SwaggerResponse.NOT_FOUND_DESCRIPTION) })
    @GetMapping(value = RestUrl.GET_BALANCE_BY_DATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinalResponse<StockBalance>> findBalanceByDate(@RequestParam String date) {
        log.info("Received request to retrieve stock balances for date: {}", date);
        FinalResponse<StockBalance> result = new FinalResponse<>();
        try {
            List<StockBalance> balances = stockService.findBalanceByDate(LocalDate.parse(date));
            result.setDataList(balances);
            result.setMessage(ResponseMessage.MSG_SUCCESS);
        } catch (IllegalArgumentException e) {
            String message = ResponseMessage.MSG_ERROR + e.getMessage();
            result.setMessage(message);
            log.error("An error occurred while retrieving stock balance by date.", e);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "Get a current stock balance", description = "Returns a current stock balance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.FOUND_CODE, description = SwaggerResponse.FOUND_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.NOT_FOUND_CODE, description = SwaggerResponse.NOT_FOUND_DESCRIPTION) })
    @GetMapping(value = RestUrl.GET_CURRENT_BALANCE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinalResponse<StockBalance>> findCurrentBalance(@RequestBody Item item) {
        log.info("Received request to retrieve current stock balance.");
        FinalResponse<StockBalance> result = new FinalResponse<>();
        try {
            StockBalance balance = stockService.getTopBalance(item);
            result.setData(balance);
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
