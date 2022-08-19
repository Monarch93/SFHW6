package ru.gb;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        CostumerDao costumerDao = context.getBean("costumerDao", CostumerDao.class);

        ProductDao productDao = context.getBean("productDao", ProductDao.class);


        costumerDao.findAllProducts(2L);
        productDao.findAllCostumers(7L);

    }
}