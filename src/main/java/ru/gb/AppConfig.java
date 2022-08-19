package ru.gb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


import javax.persistence.EntityManagerFactory;

@Configuration
@ComponentScan("ru.gb")
public class AppConfig {

    @Bean
    public EntityManagerFactory serviceFactory() {
        return new ServiceFactory().getEntityManagerFactory();
    }

    @Bean
    public CostumerDao costumerDao(EntityManagerFactory emf) {
        return new CostumerDao(emf);
    }

    @Bean
    public ProductDao productDao(EntityManagerFactory emf) {
        return new ProductDao(emf);
    }
}
