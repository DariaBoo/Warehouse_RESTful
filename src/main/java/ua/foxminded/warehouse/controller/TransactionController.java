package ua.foxminded.warehouse.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import ua.foxminded.warehouse.controller.messages.ResponseMessage;
import ua.foxminded.warehouse.controller.swagger_response.SwaggerResponse;
import ua.foxminded.warehouse.controller.urls.RestUrl;
import ua.foxminded.warehouse.service.TransactionService;
import ua.foxminded.warehouse.service.dto.FinalResponse;
import ua.foxminded.warehouse.service.entities.Transaction;
import ua.foxminded.warehouse.service.exceptions.EntityNotFoundException;
import ua.foxminded.warehouse.service.exceptions.EntityServiceException;

/**
 * REST controller for managing transactions.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Slf4j
@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    /**
     * Creates a new transaction in the database.
     *
     * @param transaction the transaction to be added
     * @return the HTTP response entity with the result of the operation
     * @throws ResponseStatusException if there was an error while creating the
     *                                 transaction
     */
    @Operation(summary = "createTransaction", description = "Add a new transaction to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.CREATED_CODE, description = SwaggerResponse.CREATED_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.SERVER_ERROR_CODE, description = SwaggerResponse.SERVER_ERROR_DESCRIPTION) })
    @PostMapping(value = RestUrl.CREATE_TRANSACTION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody Transaction transaction) {
        log.info("Received request to create transaction.");
        boolean isCreated = false;
        try {
            isCreated = transactionService.create(transaction);
            if (isCreated) {
                return new ResponseEntity<>(ResponseMessage.TRANSACTION_CREATED, HttpStatus.CREATED);
            }
        } catch (IllegalArgumentException | EntityServiceException e) {
            String message = "message: " + e.getMessage();
            HttpStatus status = e instanceof EntityServiceException ? HttpStatus.INTERNAL_SERVER_ERROR
                    : HttpStatus.BAD_REQUEST;
            log.error("An error occurred while creating transaction", e);
            throw new ResponseStatusException(status, message, e);
        }
        log.error("Failed to create transaction.");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Updates an existing transaction in the database.
     *
     * @param id          the ID of the transaction to be updated
     * @param transaction the updated transaction data
     * @return the HTTP response entity with the result of the operation
     */
    @Operation(summary = "updateTransaction", description = "Update transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.UPDATED_CODE, description = SwaggerResponse.UPDATED_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.SERVER_ERROR_CODE, description = SwaggerResponse.SERVER_ERROR_DESCRIPTION) })
    @PutMapping(value = RestUrl.UPDATE_TRANSACTION, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Transaction transaction) {
        log.info("Received request to update the transaction. ID: {}", id);
        try {
            transactionService.update(transaction);
            return new ResponseEntity<>(ResponseMessage.TRANSACTION_UPDATED, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating transaction with id: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes an existing transaction from the database.
     *
     * @param id the ID of the transaction to be deleted
     * @return the HTTP response entity with the result of the operation
     */
    @Operation(summary = "deleteTransaction", description = "Delete transaction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.DELETED_CODE, description = SwaggerResponse.DELETED_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.SERVER_ERROR_CODE, description = SwaggerResponse.SERVER_ERROR_DESCRIPTION) })
    @DeleteMapping(value = RestUrl.REMOVE_TRANSACTION, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<String> remove(@PathVariable Integer id) {
        log.info("Received request to remove the transaction. ID: {}", id);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String body = "";
        try {
         transactionService.remove(id);
         status = HttpStatus.NO_CONTENT;
         body = ResponseMessage.TRANSACTION_REMOVED;
     } catch (EntityNotFoundException e) {
         status = HttpStatus.NOT_FOUND;
         body = ResponseMessage.MSG_ERROR;
     }
     return ResponseEntity.status(status).body(body);
    }

    /**
     * Retrieves a transaction from the database by its ID.
     *
     * @param id the ID of the transaction to retrieve
     * @return the HTTP response entity with the retrieved transaction data
     */
    @Operation(summary = "Get a transaction by id", description = "Returns the transaction with the specified ID")
    @ApiResponses(value = { @ApiResponse(responseCode = SwaggerResponse.FOUND_CODE, description = SwaggerResponse.FOUND_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.NOT_FOUND_CODE, description = SwaggerResponse.NOT_FOUND_DESCRIPTION) })
    @GetMapping(value = RestUrl.FIND_TRANSACTION_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinalResponse<Transaction>> findById(@PathVariable Integer id) {
        log.info("Received request to find the transaction. ID: {}", id);
        FinalResponse<Transaction> result = new FinalResponse<>();
        try {
            Transaction transaction = transactionService.findById(id);
            result.setData(transaction);
            result.setMessage(ResponseMessage.MSG_SUCCESS);
        } catch (IllegalArgumentException e) {
            String message = ResponseMessage.MSG_ERROR + e.getMessage();
            result.setMessage(message);
            log.error("An error occurred while retrieving transaction by id.", e);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieves a list of transactions from the database that match a given date
     * and item ID.
     *
     * @param date   the date to search for (in yyyy-MM-dd format)
     * @param itemId the ID of the item to search for
     * @return the HTTP response entity with the list of retrieved transactions
     */
    @Operation(summary = "List all transaction by date and item", description = "Returns a list of transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.FOUND_CODE, description = SwaggerResponse.FOUND_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.NOT_FOUND_CODE, description = SwaggerResponse.NOT_FOUND_DESCRIPTION) })
    @GetMapping(value = RestUrl.FIND_TRANSACTION_BY_DATE_AND_ITEM, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinalResponse<Transaction>> findByDateAndItem(@RequestParam String date, @RequestParam Integer itemId) {
        log.info("Received request to find transactions by date and item.");
        FinalResponse<Transaction> result = new FinalResponse<>();
        try {
            List<Transaction> transactions = transactionService.findByDateAndItem(LocalDate.parse(date), itemId);
            result.setDataList(transactions);
            result.setMessage(ResponseMessage.MSG_SUCCESS);
        } catch (IllegalArgumentException e) {
            String message = ResponseMessage.MSG_ERROR + e.getMessage();
            result.setMessage(message);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieves a list of transactions from the database that match a given date.
     * 
     * @param date the date to search for (in yyyy-MM-dd format)
     * @return the HTTP response entity with the list of retrieved transactions
     */
    @Operation(summary = "List all transaction by date", description = "Returns a list of transactions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.FOUND_CODE, description = SwaggerResponse.FOUND_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.NOT_FOUND_CODE, description = SwaggerResponse.NOT_FOUND_DESCRIPTION) })
    @GetMapping(value = RestUrl.FIND_TRANSACTION_BY_DATE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinalResponse<Transaction>> findByDate(@RequestParam String date) {
        log.info("Received request to find transactions by date.");
        FinalResponse<Transaction> result = new FinalResponse<>();
        try {
            List<Transaction> transactions = transactionService.findByDate(LocalDate.parse(date));
            result.setDataList(transactions);
            result.setMessage(ResponseMessage.MSG_SUCCESS);
            log.info("Transactions for date: {} were found successfully!", date);
        } catch (IllegalArgumentException e) {
            String message = ResponseMessage.MSG_ERROR + e.getMessage();
            result.setMessage(message);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
