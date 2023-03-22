package ua.foxminded.warehouse.service;

import java.util.List;

import ua.foxminded.warehouse.service.entities.Address;

/**
 * The AddressService interface provides a set of methods to manage address entities.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
public interface AddressService {

    /**
     * Creates a new address.
     * 
     * @param newAddress the address to create
     * @return true if the address was created successfully, false otherwise
     */
    boolean create(Address newAddress);

    /**
     * Updates an existing address.
     * 
     * @param address the address to update
     */
    void update(Address address);

    /**
     * Removes an address by ID.
     * 
     * @param id the ID of the address to remove
     */
    void remove(int id);

    /**
     * Returns an address by ID.
     * 
     * @param id the ID of the address to retrieve
     * @return the address with the given ID
     * @throws IllegalArgumentException if no address with the specified ID is found.
     */
    Address findById(int id);
    
    /**
     * Returns a list of partner's addresses by partner ID.
     * 
     * @param id the ID of the partner to retrieve an address
     * @return the address with the given ID
     * @throws IllegalArgumentException if no address with the specified ID is found.
     */
    List<Address> findByPartnerId(int partnerId);
}
