package ru.gb;

import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class ServiceFactory {
    EntityManagerFactory entityManagerFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .buildSessionFactory();

    @Autowired
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
}