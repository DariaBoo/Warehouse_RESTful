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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import ua.foxminded.warehouse.controller.messages.ResponseMessage;
import ua.foxminded.warehouse.controller.swagger_response.SwaggerResponse;
import ua.foxminded.warehouse.controller.urls.RestUrl;
import ua.foxminded.warehouse.service.CategoryService;
import ua.foxminded.warehouse.service.dto.FinalResponse;
import ua.foxminded.warehouse.service.entities.Category;
import ua.foxminded.warehouse.service.exceptions.EntityNotFoundException;

/**
 * REST controller for managing categories.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Slf4j
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Creates a new category in the database.
     * 
     * @param newCategory the category to be added
     * @return the HTTP response entity with the result of the operation
     */
    @Operation(summary = "createCategory", description = "Add a new category to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.CREATED_CODE, description = SwaggerResponse.CREATED_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.SERVER_ERROR_CODE, description = SwaggerResponse.SERVER_ERROR_DESCRIPTION) })
    @PostMapping(value = RestUrl.CREATE_CATEGORY, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody Category newCategory) {
        log.info("Received request to create category.");
        boolean isCreated = false;
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String body = "";
        try {
            isCreated = categoryService.create(newCategory);
            if (isCreated) {
                status = HttpStatus.CREATED;
                body = ResponseMessage.CATEGORY_CREATED;
            }
        } catch (Exception e) {
            log.error("Failed to create category.", e);
            body = ResponseMessage.MSG_ERROR;
        }
        return ResponseEntity.status(status).body(body);
    }

    /**
     * Updates an existing category in the database.
     * 
     * @param id       the ID of the category to be updated
     * @param category the updated category data
     * @return the HTTP response entity with the result of the operation
     */
    @Operation(summary = "updateCategory", description = "Update category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.UPDATED_CODE, description = SwaggerResponse.UPDATED_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.SERVER_ERROR_CODE, description = SwaggerResponse.SERVER_ERROR_DESCRIPTION) })
    @PutMapping(value = RestUrl.UPDATE_CATEGORY, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody Category category) {
        log.info("Received request to update category. ID: {}", id);
        try {
            Category updatedCategory = categoryService.findById(id);
            updatedCategory.setName(category.getName());
            categoryService.update(updatedCategory);
            return new ResponseEntity<>(ResponseMessage.CATEGORY_UPDATED, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error updating category with id: {}", id, e);
            return new ResponseEntity<>(ResponseMessage.MSG_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes an existing category from the database.
     * 
     * @param id the ID of the category to be deleted
     * @return the HTTP response entity with the result of the operation
     */
    @Operation(summary = "deleteCategory", description = "Delete category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.DELETED_CODE, description = SwaggerResponse.DELETED_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.SERVER_ERROR_CODE, description = SwaggerResponse.SERVER_ERROR_DESCRIPTION) })
    @DeleteMapping(value = RestUrl.REMOVE_CATEGORY, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> remove(@PathVariable Integer id) {
        log.info("Received request to delete category. ID: {}", id);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String body = "";
        try {
            categoryService.remove(id);
            status = HttpStatus.NO_CONTENT;
            body = ResponseMessage.CATEGORY_REMOVED;
        } catch (EntityNotFoundException e) {
            status = HttpStatus.NOT_FOUND;
            body = ResponseMessage.MSG_ERROR;
        }
        return ResponseEntity.status(status).body(body);
    }

    /**
     * Retrieves a category from the database by its ID.
     * 
     * @param id the ID of the category to retrieve
     * @return the HTTP response entity with the retrieved category data
     */
    @Operation(summary = "Get a category by id", description = "Returns the category with the specified ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.FOUND_CODE, description = SwaggerResponse.FOUND_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.NOT_FOUND_CODE, description = SwaggerResponse.NOT_FOUND_DESCRIPTION) })
    @GetMapping(value = RestUrl.FIND_CATEGORY_BY_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinalResponse<Category>> findById(@PathVariable Integer id) {
        log.info("Received request to find the category. ID: {}", id);
        FinalResponse<Category> result = new FinalResponse<>();
        try {
            Category category = categoryService.findById(id);
            result.setData(category);
            result.setMessage(ResponseMessage.MSG_SUCCESS);
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            result.setMessage(message);
            log.error("An error occurred while retrieving category by id.", e);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * Retrieves a list of all categories from the database.
     * 
     * @return the HTTP response entity with the list of retrieved categories
     */
    @Operation(summary = "List all categories", description = "Returns a list of categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = SwaggerResponse.FOUND_CODE, description = SwaggerResponse.FOUND_DESCRIPTION),
            @ApiResponse(responseCode = SwaggerResponse.NOT_FOUND_CODE, description = SwaggerResponse.NOT_FOUND_DESCRIPTION) })
    @GetMapping(value = RestUrl.FIND_ALL_CATEGORIES, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinalResponse<Category>> findAll() {
        log.info("Received request to find all categories.");
        FinalResponse<Category> result = new FinalResponse<>();
        try {
            List<Category> categories = categoryService.findAll();
            result.setDataList(categories);
            result.setMessage(ResponseMessage.MSG_SUCCESS);
        } catch (Exception e) {
            String message = ResponseMessage.MSG_ERROR + e.getMessage();
            result.setMessage(message);
            log.error("An error occurred while retrieving categories.", e);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
