package ua.foxminded.warehouse.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ua.foxminded.warehouse.dao.CategoryDAO;
import ua.foxminded.warehouse.service.CategoryService;
import ua.foxminded.warehouse.service.entities.Category;
import ua.foxminded.warehouse.service.exceptions.EntityNotFoundException;

/**
 * Service implementation class for managing categories. This class implements
 * the {@link CategoryService} interface and provides methods to create, update,
 * remove, and retrieve categories from the database.
 * 
 * @author Bohush Darya
 * @version 1.0
 *
 */
@Slf4j
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDAO categoryDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean create(Category newCategory) {
        boolean isCreated = false;
        categoryDao.save(newCategory);
        isCreated = categoryDao.existsById(newCategory.getId());
        log.info("Category created successfully. ID: {}", newCategory.getId());
        return isCreated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(Category category) {
        categoryDao.save(category);
        log.info("Category updated successfully. ID: {}", category.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(int id) {
        if (categoryDao.existsById(id)) {
            categoryDao.deleteById(id);
            log.info("Category removed. ID: {}", id);
        } else {
            log.error("Failed to remove category. ID: %s. Entity not found.", id);
            throw new EntityNotFoundException("Category not found with ID: " + id);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Cacheable("groups")
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        List<Category> categories = categoryDao.findAll();
        log.info("Found a list with all categories [size::{}]", categories.size());
        return categories;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Category findById(int id) {
        Category category = categoryDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No category with id " + id));
        log.info("Category found by ID. ID: {}", id);
        return category;
    }
}
