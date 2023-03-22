package ua.foxminded.warehouse.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import config.CacheConfigTest;
import ua.foxminded.warehouse.AppSpringBoot;
import ua.foxminded.warehouse.dao.UnitDAO;
import ua.foxminded.warehouse.service.CategoryService;
import ua.foxminded.warehouse.service.ItemService;
import ua.foxminded.warehouse.service.entities.Category;
import ua.foxminded.warehouse.service.entities.Item;
import ua.foxminded.warehouse.service.entities.Unit;

@ContextConfiguration(classes = CacheConfigTest.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AppSpringBoot.class)
class ItemServiceImplTest {

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private ItemService itemService;
    @Autowired
    private CategoryService groupService;
    @Autowired
    private UnitDAO unitDao;

    @BeforeEach
    void setUp() {
        Category category = new Category();
        category.setName("category1");
        groupService.create(category);
        Unit unit = new Unit();
        unit.setName("Psc");
        unitDao.save(unit);
        Item item = Item.builder().name("ItemTest").code("000").location("A0").category(category).unit(unit)
                .sellingPrice(BigDecimal.TEN).build();
        itemService.create(item);
    }

    private Optional<List<Item>> getCachedItem() {
        return Optional.ofNullable((List<Item>) cacheManager.getCache("items").get("findAll").get());
    }

    @Test
    void test() {
        List<Item> items = itemService.findAll();
        assertEquals(items, getCachedItem().get());
    }
}
