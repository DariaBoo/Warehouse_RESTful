package ua.foxminded.warehouse.service;

import java.util.List;

import ua.foxminded.warehouse.service.entities.Item;

/**
 * This interface defines the operations that can be performed on an Item.
 * It provides methods for creating, updating, deleting, and finding items.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
public interface ItemService {

    /**
     * Creates a new item in the database.
     * 
     * @param item the item to create
     * @return true if the item was created successfully, false otherwise
     */
    boolean create(Item item);

    /**
     * Updates an existing item in the database.
     * 
     * @param item the item to update
     */
    void update(Item item);

    /**
     * Removes an item from the database.
     * 
     * @param itemId the ID of the item to remove
     */
    void remove(long itemId);

    /**
     * Finds all items in the system.
     * 
     * @return a list of all items
     */
    List<Item> findAll();

    /**
     * Finds an item by its ID.
     * @param itemId the ID of the item to find
     * @return the item with the specified ID
     * @throws IllegalArgumentException if no item with the specified ID is found.
     */
    Item findById(long itemId);
}
