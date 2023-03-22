package ua.foxminded.warehouse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ua.foxminded.warehouse.service.entities.Category;

/**
 * The CategoryDAO interface provides access to the database for performing CRUD operations on the Category entity.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Repository
public interface CategoryDAO extends JpaRepository<Category, Integer> {

}
