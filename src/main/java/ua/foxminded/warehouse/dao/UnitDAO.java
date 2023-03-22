package ua.foxminded.warehouse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.warehouse.service.entities.Unit;

/**
 * The UnitDAO interface provides access to the database for performing CRUD operations on the Unit entity.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Repository
public interface UnitDAO extends JpaRepository<Unit, Integer> {

}
