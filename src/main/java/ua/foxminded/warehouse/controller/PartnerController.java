package ua.foxminded.warehouse.controller;

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
import ua.foxminded.warehouse.service.PartnerService;
import ua.foxminded.warehouse.service.dto.FinalResponse;
import ua.foxminded.warehouse.service.entities.Partner;
import ua.foxminded.warehouse.service.exceptions.EntityNotFoundException;

/**
 * REST controller for managing partners.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Slf4j
@RestController
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    /**
     * Creates a new partner in the database.
     *
     * @param partner the partner to be added
     * @return the HTTP response entity with the result of the operation
     * @throws ResponseStatusException if there was an error while creating the
     *                                 partner
     */
    @Operation(summary = "createPartner", description = "Add a new partner to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.CREATED_CODE, description = SwaggerResponse.CREATED_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.SERVER_ERROR_CODE, description = SwaggerResponse.SERVER_ERROR_DESCRIPTION) })
    @PostMapping(value = RestUrl.CREATE_PARTNER, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> create(@RequestBody Partner newPartner) {
        log.info("Received request to create new partner.");
        boolean isCreated = false;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String body = "";
        try {
            isCreated = partnerService.create(newPartner);
            if (isCreated) {
                status = HttpStatus.CREATED;
                body = ResponseMessage.PARTNER_CREATED;
            }
        } catch (Exception e) {
            body = ResponseMessage.MSG_ERROR;
            log.error("An error occurred while creating partner", e);
        }
        return ResponseEntity.status(status).body(body);
    }

    /**
     * Updates an existing partner in the database.
     *
     * @param id      the ID of the partner to be updated
     * @param partner the updated partner data
     * @return the HTTP response entity with the result of the operation
     */
    @Operation(summary = "updatePartner", description = "Update partner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.UPDATED_CODE, description = SwaggerResponse.UPDATED_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.SERVER_ERROR_CODE, description = SwaggerResponse.SERVER_ERROR_DESCRIPTION) })
    @PutMapping(value = RestUrl.UPDATE_PARTNER, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Partner partner) {
        log.info("Received request to update the partner. ID: {}", id);
        try {
            partnerService.update(partner);
            return new ResponseEntity<>(ResponseMessage.PARTNER_UPDATED, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating partner with id: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes an existing partner from the database.
     *
     * @param id the ID of the partner to be deleted
     * @return the HTTP response entity with the result of the operation
     */
    @Operation(summary = "deletePartner", description = "Delete partner")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.DELETED_CODE, description = SwaggerResponse.DELETED_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.SERVER_ERROR_CODE, description = SwaggerResponse.SERVER_ERROR_DESCRIPTION) })
    @DeleteMapping(value = RestUrl.REMOVE_PARTNER, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> remove(@PathVariable Integer id) {
        log.info("Received request to remove the partner. ID: {}", id);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String body = "";
        try {
            partnerService.remove(id);
            status = HttpStatus.NO_CONTENT;
            body = ResponseMessage.PARTNER_REMOVED;
        } catch (EntityNotFoundException e) {
            status = HttpStatus.NOT_FOUND;
            body = ResponseMessage.MSG_ERROR;
        }
        return ResponseEntity.status(status).body(body);
    }

    /**
     * Retrieves a partner from the database by its ID.
     *
     * @param id the ID of the partner to retrieve
     * @return the HTTP response entity with the retrieved transaction data
     */
    @Operation(summary = "Get a partner by id", description = "Returns a partner as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.FOUND_CODE, description = SwaggerResponse.FOUND_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.NOT_FOUND_CODE, description = SwaggerResponse.NOT_FOUND_DESCRIPTION) })
    @GetMapping(value = RestUrl.FIND_PARTNER_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Partner> findById(@PathVariable Integer id) {
        log.info("Received request to find the partner. ID: {}", id);
        try {
            return new ResponseEntity<>(partnerService.findById(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("An error occurred while retrieving partner by id.", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Operation(summary = "List all customers", description = "Returns a list of customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.FOUND_CODE, description = SwaggerResponse.FOUND_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.NOT_FOUND_CODE, description = SwaggerResponse.NOT_FOUND_DESCRIPTION) })
    @GetMapping(value = RestUrl.FIND_ALL_CUSTOMERS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinalResponse<Partner>> findAllCustomers() {
        log.info("Received request to find the partner all customers");
        FinalResponse<Partner> result = new FinalResponse<>();
        try {
            List<Partner> partners = partnerService.findAllCustomers();
            result.setDataList(partners);
            result.setMessage(ResponseMessage.MSG_SUCCESS);
        } catch (IllegalArgumentException e) {
            result.setMessage(ResponseMessage.MSG_ERROR);
            log.error("An error occurred while retrieving customers.", e);
            new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(summary = "List all suppliers", description = "Returns a list of suppliers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.FOUND_CODE, description = SwaggerResponse.FOUND_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.NOT_FOUND_CODE, description = SwaggerResponse.NOT_FOUND_DESCRIPTION) })
    @GetMapping(value = RestUrl.FIND_ALL_SUPPLIERS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinalResponse<Partner>> findAllSuppliers() {
        log.info("Received request to find the partner all suppliers");
        FinalResponse<Partner> result = new FinalResponse<>();
        try {
            List<Partner> partners = partnerService.findAllSuppliers();
            result.setDataList(partners);
            result.setMessage(ResponseMessage.MSG_SUCCESS);
        } catch (IllegalArgumentException e) {
            result.setMessage(ResponseMessage.MSG_ERROR);
            log.error("An error occurred while retrieving suppliers.", e);
            new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
