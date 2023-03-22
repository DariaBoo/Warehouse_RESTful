package ua.foxminded.warehouse.service.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import config.CacheConfigTest;
import ua.foxminded.warehouse.AppSpringBoot;
import ua.foxminded.warehouse.dao.PartnerDAO;
import ua.foxminded.warehouse.service.PartnerService;
import ua.foxminded.warehouse.service.entities.Partner;
import ua.foxminded.warehouse.service.enums.PartnerType;

@ContextConfiguration(classes = CacheConfigTest.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AppSpringBoot.class)
@TestInstance(Lifecycle.PER_CLASS)
class PartnerServiceImplTest {

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private PartnerService partnerService;
    @Autowired
    private PartnerDAO partnerDao;

    @BeforeAll
    void setUp() {
        new Partner();
        Partner customer = Partner.builder().name("test").tin("222").type(PartnerType.CUSTOMER).build();
        partnerDao.save(customer);
        Partner supplier = Partner.builder().name("test1").tin("111").type(PartnerType.SUPPLIER).build();
        partnerDao.save(supplier);
    }

    private Optional<List<Partner>> getCachedCustomers() {
        return Optional.ofNullable((List<Partner>)cacheManager.getCache("customers").get("findAllCustomers").get());
    }
    
    private Optional<List<Partner>> getCachedSuppliers() {
        return Optional.ofNullable((List<Partner>)cacheManager.getCache("suppliers").get("findAllSuppliers").get());
    }

    @Test
    void findAllCustomers_shouldReturnListFromCache() {
        List<Partner> customers = partnerService.findAllCustomers();
        assertEquals(customers, getCachedCustomers().get());
    }
    
    @Test
    void findAllSuppliers_shouldReturnListFromCache() {
        List<Partner> suppliers = partnerService.findAllSuppliers();
        assertEquals(suppliers, getCachedSuppliers().get());
    }
}
