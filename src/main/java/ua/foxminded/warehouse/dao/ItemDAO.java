package ua.foxminded.warehouse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.warehouse.service.entities.Item;

/**
 * The ItemDAO interface provides access to the database for performing CRUD operations on the Item entity.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Repository
public interface ItemDAO extends JpaRepository<Item, Long>{
    
}
