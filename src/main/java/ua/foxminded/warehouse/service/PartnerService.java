package ua.foxminded.warehouse.service;

import java.util.List;

import ua.foxminded.warehouse.service.entities.Partner;

/**
 * The PartnerService interface provides methods for creating, updating, and
 * removing partner records, as well as retrieving lists of customers or
 * suppliers, and finding individual partners by ID.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
public interface PartnerService {

    /**
     * Creates a new partner record with the given data.
     *
     * @param newPartner the Partner object representing the new partner to create.
     * @return true if the partner was created successfully, false otherwise.
     */
    boolean create(Partner newPartner);

    /**
     * Updates an existing partner record with the given data.
     *
     * @param partner the Partner object representing the updated partner data.
     */
    void update(Partner partner);

    /**
     * Removes an existing partner record with the given ID.
     *
     * @param id the ID of the partner to remove.
     */
    void remove(int id);
    
    /**
     * Retrieves a list of all customers in the database.
     *
     * @return a List of Partner objects representing all customers in the database.
     * @throws IllegalArgumentException
     */
    List<Partner> findAllCustomers();
    
    /**
     * Retrieves a list of all suppliers in the database.
     *
     * @return a List of Partner objects representing all suppliers in the database.
     * @throws IllegalArgumentException
     */
    List<Partner> findAllSuppliers(); 
    
    /**
     * Finds a single partner record with the given ID.
     *
     * @param id the ID of the partner to find.
     * @return the Partner with the given ID
     * @throws IllegalArgumentException if no partner with the specified ID is found.
     */
    Partner findById(int id);
}
