package ua.foxminded.warehouse.controller.swagger_response;

/**
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
public final class SwaggerResponse {

    public static final String FOUND_CODE = "200";
    public static final String FOUND_DESCRIPTION = "Successfully retrieved";
    public static final String UPDATED_CODE = "200";
    public static final String UPDATED_DESCRIPTION = "Entity was updated";
    public static final String CREATED_CODE = "201";
    public static final String CREATED_DESCRIPTION = "Entity was added to database";
    public static final String DELETED_CODE = "204";
    public static final String DELETED_DESCRIPTION = "Entity was deleted";
    public static final String NOT_FOUND_CODE = "404";
    public static final String NOT_FOUND_DESCRIPTION = "Not found - Entity was not found";
    public static final String SERVER_ERROR_CODE = "500";
    public static final String SERVER_ERROR_DESCRIPTION = "Something went wrong";

    private SwaggerResponse() {

    }
}
