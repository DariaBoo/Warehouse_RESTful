package ua.foxminded.warehouse.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.warehouse.service.entities.Address;

/**
 * The AddressDAO interface provides access to the database for performing CRUD
 * operations on the Address entity.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Repository
public interface AddressDAO extends JpaRepository<Address, Integer> {

    /**
     * Retrieves a list of addresses associated with the specified partner ID.
     * 
     * @param id the ID of the partner whose addresses to retrieve
     * @return an Optional containing a List of Address objects, or an empty
     *         Optional if no addresses were found
     */
    Optional<List<Address>> findByPartnerId(int id);

}
