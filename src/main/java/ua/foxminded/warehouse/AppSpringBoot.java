package ua.foxminded.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableCaching
@SpringBootApplication(scanBasePackages = { "ua.foxminded.warehouse" })
@EnableJpaRepositories(basePackages = {"ua.foxminded.warehouse.dao"})
@EntityScan(basePackages = "ua.foxminded.warehouse.service.entities, ua.foxminded.warehouse.service.dto")
public class AppSpringBoot extends SpringBootServletInitializer {

    public static void main(String[] args) {
        System.setProperty("server.servlet.context-path", "/warehouse");
        SpringApplication.run(AppSpringBoot.class, args);
    }
}
