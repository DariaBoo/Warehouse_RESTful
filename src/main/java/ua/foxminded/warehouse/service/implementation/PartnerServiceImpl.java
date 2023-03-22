package ua.foxminded.warehouse.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.warehouse.dao.PartnerDAO;
import ua.foxminded.warehouse.service.PartnerService;
import ua.foxminded.warehouse.service.entities.Partner;
import ua.foxminded.warehouse.service.exceptions.EntityNotFoundException;

/**
 * Service implementation class for managing partners. This class implements
 * the {@link PartnerService} interface and provides methods to create,
 * update, remove, and retrieve customers and suppliers from the database.
 * 
 * @author Bohush Darya
 * @version 1.0
 */
@Slf4j
@Service
@Transactional
public class PartnerServiceImpl implements PartnerService {

    @Autowired
    private PartnerDAO partnerDao;
    
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean create(Partner newPartner) {
        partnerDao.save(newPartner);
        boolean isCreated = partnerDao.existsById(newPartner.getId());
        log.info("Partner created successfully. ID: {}", newPartner.getId());
        return isCreated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Partner partner) {
        partnerDao.save(partner);
        log.info("Partner updated successfully. ID: {}", partner.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(int id) {
        if(partnerDao.existsById(id)) {
        partnerDao.deleteById(id);
        log.info("Partner removed. ID: {}", id);
        } else {
            log.error("Failed to remove partner. ID: %s. Entity not found.", id);
            throw new EntityNotFoundException("Partner not found with ID: " + id);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable("customers")
    public List<Partner> findAllCustomers(){
        List<Partner> customers = partnerDao.findAllCustomers().orElseThrow(() -> new IllegalArgumentException("No customers"));
        log.info("Found a list of all customers. SIZE: {}", customers.size());
        return customers;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable("suppliers")
    public List<Partner> findAllSuppliers(){
        List<Partner> suppliers = partnerDao.findAllSuppliers().orElseThrow(() -> new IllegalArgumentException("No suppliers"));
        log.info("Found a list of all suppliers. SIZE: {}", suppliers.size());
        return suppliers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Partner findById(int id) {
        Partner partner = partnerDao.findById(id).orElseThrow(() -> new IllegalArgumentException("No partner with id " + id));
        log.info("Partner was found successfully! ID: {}", id);
        return partner;
    }
}
