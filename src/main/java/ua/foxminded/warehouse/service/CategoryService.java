package ua.foxminded.warehouse.service;

import java.util.List;

import ua.foxminded.warehouse.service.entities.Category;

/**
 * The CategoryService interface provides a set of methods to manage category entities.
 * Categories are used to group items, making it easier to track and manage expenses.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
public interface CategoryService {

    /**
     * Creates a new category.
     * 
     * @param newCategory the category to create
     * @return true if the category was created successfully, false otherwise
     */
    boolean create(Category newCategory);

    /**
     * Updates an existing category.
     * 
     * @param category the category to update
     */
    void update(Category category);

    /**
     * Removes a category by ID.
     * 
     * @param id the ID of the category to remove
     */
    void remove(int id);

    /**
     * Returns a list of all categories.
     * 
     * @return a list of all categories
     */
    List<Category> findAll();

    /**
     * Returns a category by ID.
     * 
     * @param id the ID of the category to retrieve
     * @return the category with the given ID
     * @throws IllegalArgumentException if no category with the specified ID is found.
     */
    Category findById(int id);
}
