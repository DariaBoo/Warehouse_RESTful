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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import ua.foxminded.warehouse.controller.messages.ResponseMessage;
import ua.foxminded.warehouse.controller.swagger_response.SwaggerResponse;
import ua.foxminded.warehouse.controller.urls.RestUrl;
import ua.foxminded.warehouse.service.ItemService;
import ua.foxminded.warehouse.service.entities.Item;
import ua.foxminded.warehouse.service.exceptions.EntityNotFoundException;

/**
 * REST controller for managing items.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Slf4j
@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * Creates a new item in the database.
     * 
     * @param item the item to be added
     * @return the HTTP response entity with the result of the operation
     */
    @Operation(summary = "createItem", description = "Add a new item to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.CREATED_CODE, description = SwaggerResponse.CREATED_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.SERVER_ERROR_CODE, description = SwaggerResponse.SERVER_ERROR_DESCRIPTION) })
    @PostMapping(value = RestUrl.CREATE_ITEM, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody Item item) {
        log.info("Received request to create item.");
        boolean isCreated = itemService.create(item);
        if (isCreated) {
            return new ResponseEntity<>(ResponseMessage.ITEM_CREATED, HttpStatus.CREATED);
        }
        log.error("Failed to create item.");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Updates an existing item in the database.
     * 
     * @param item the updated item data
     * @return the HTTP response entity with the result of the operation
     */
    @Operation(summary = "updateItem", description = "Update item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.UPDATED_CODE, description = SwaggerResponse.UPDATED_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.SERVER_ERROR_CODE, description = SwaggerResponse.SERVER_ERROR_DESCRIPTION) })
    @PutMapping(value = RestUrl.UPDATE_ITEM, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Item item) {
        log.info("Received request to update item. ID: {}", item.getId());
        try {
            itemService.update(item);
        } catch (Exception e) {
            log.info("Error updating item with id: {}", item.getId(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ResponseMessage.ITEM_UPDATED, HttpStatus.OK);
    }

    /**
     * Deletes an existing item from the database.
     * 
     * @param id the ID of the item to be deleted
     * @return the HTTP response entity with the result of the operation
     */
    @Operation(summary = "deleteItem", description = "Delete item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.DELETED_CODE, description = SwaggerResponse.DELETED_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.SERVER_ERROR_CODE, description = SwaggerResponse.SERVER_ERROR_DESCRIPTION) })
    @DeleteMapping(value = RestUrl.REMOVE_ITEM, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> remove(@PathVariable Integer id) {
        log.info("Received request to delete item. ID: {}", id);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String body = "";
        try {
            itemService.remove(id);
            status = HttpStatus.NO_CONTENT;
            body = ResponseMessage.ITEM_REMOVED;
        } catch (EntityNotFoundException e) {
            status = HttpStatus.NOT_FOUND;
            body = ResponseMessage.MSG_ERROR;
        }
        return ResponseEntity.status(status).body(body);
    }

    /**
     * Retrieves a list of all items from the database.
     * 
     * @return the HTTP response entity with the list of retrieved items
     */
    @Operation(summary = "List all items", description = "Returns a list of items")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.FOUND_CODE, description = SwaggerResponse.FOUND_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.NOT_FOUND_CODE, description = SwaggerResponse.NOT_FOUND_DESCRIPTION) })
    @GetMapping(value = RestUrl.FIND_ALL_ITEMS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Item>> findAll() {
        log.info("Received request to find all items.");
        return new ResponseEntity<>(itemService.findAll(), HttpStatus.OK);
    }

    /**
     * Retrieves an item from the database by its ID.
     * 
     * @param id the ID of the item to retrieve
     * @return the HTTP response entity with the retrieved item data
     */
    @Operation(summary = "Get an item by id", description = "Returns an item as per the id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.FOUND_CODE, description = SwaggerResponse.FOUND_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.NOT_FOUND_CODE, description = SwaggerResponse.NOT_FOUND_DESCRIPTION) })
    @GetMapping(value = RestUrl.FIND_ITEM_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> findById(@PathVariable Integer id) {
        log.info("Received request to find the item. ID: {}", id);
        try {
            return new ResponseEntity<>(itemService.findById(id), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("An error occurred while retrieving item by id.", e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
