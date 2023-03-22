package ua.foxminded.warehouse.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.warehouse.dao.AddressDAO;
import ua.foxminded.warehouse.service.AddressService;
import ua.foxminded.warehouse.service.entities.Address;
import ua.foxminded.warehouse.service.exceptions.EntityNotFoundException;

/**
 * Service implementation class for managing addresses. This class implements
 * the {@link AddressService} interface and provides methods to create, update,
 * remove, and retrieve address from the database.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Slf4j
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDAO addressDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean create(Address newAddress) {
        boolean isCreated = false;
        addressDao.save(newAddress);
        isCreated = addressDao.existsById(newAddress.getId());
        log.info("Address created successfully. ID: {}", newAddress.getId());
        return isCreated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Address address) {
        addressDao.save(address);
        log.info("Address updated successfully. ID: {}", address.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(int id) {
        if (addressDao.existsById(id)) {
            addressDao.deleteById(id);
            log.info("Address removed. ID: {}", id);
        } else {
            log.error("Failed to remove address. ID: %s. Entity not found.", id);
            throw new EntityNotFoundException("Address not found with ID: " + id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Address findById(int id) {
        Address address = addressDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No address with id " + id));
        log.info("Address found by ID. ID: {}", id);
        return address;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Address> findByPartnerId(int id) {
        List<Address> addresses = addressDao.findByPartnerId(id)
                .orElseThrow(() -> new IllegalArgumentException("No addresses for partner with id " + id));
        log.info("Address found by partner ID. ID: {}", id);
        return addresses;
    }
}
