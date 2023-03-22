package ua.foxminded.warehouse.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ua.foxminded.warehouse.service.entities.Partner;

/**
 * The PartnerDAO interface provides access to the database for performing CRUD operations on the Partner entity.
 *
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Repository
public interface PartnerDAO extends JpaRepository<Partner, Integer>{

    /**
     * The method returns list of all customers from the table 'partners'
     * @return - list of customers
     */
    @Query("SELECT p FROM Partner p WHERE p.type = 'CUSTOMER'")
    @Cacheable(value = "customers", key = "#root.method.name")
    Optional<List<Partner>> findAllCustomers();
    
    /**
     * The method returns list of all suppliers from the table 'partners'
     * @return - list of suppliers
     */
    @Query("SELECT p FROM Partner p WHERE p.type = 'SUPPLIER'")
    @Cacheable(value = "suppliers", key = "#root.method.name")
    Optional<List<Partner>> findAllSuppliers();
}
