package ua.foxminded.warehouse.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.warehouse.dao.ItemDAO;
import ua.foxminded.warehouse.service.ItemService;
import ua.foxminded.warehouse.service.entities.Item;
import ua.foxminded.warehouse.service.exceptions.EntityNotFoundException;

/**
 * Service implementation class for managing items. This class implements the
 * {@link ItemService} interface and provides methods to create, update, remove,
 * and retrieve items from the database.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Slf4j
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDAO itemDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean create(Item item) {
        itemDao.save(item);
        boolean isCreated = itemDao.existsById(item.getId());
        log.info("Item created successfully. ID: {}", item.getId());
        return isCreated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Item item) {
        itemDao.save(item);
        log.info("Item updated successfully. ID: {}", item.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(long id) {
        if (itemDao.existsById(id)) {
            itemDao.deleteById(id);
            log.info("Item removed. ID: {}", id);
        } else {
            log.error("Failed to remove item. ID: %s. Entity not found.", id);
            throw new EntityNotFoundException("Item not found with ID: " + id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "items", key = "#root.method.name")
    public List<Item> findAll() {
        List<Item> items = itemDao.findAll();
        log.info("Found a list with all items [size::{}]", items.size());
        return items;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Item findById(long id) {
        Item item = itemDao.findById(id).orElseThrow(() -> new IllegalArgumentException("No item with id " + id));
        log.info("Item found by ID. ID: {}", id);
        return item;
    }
}
