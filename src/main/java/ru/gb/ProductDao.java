package ru.gb;

import ru.gb.tables.Product;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Component("productDao")
public class ProductDao {

    private final EntityManagerFactory entityManagerFactory;

    public ProductDao(EntityManagerFactory emf) {
        this.entityManagerFactory = emf;
    }


    public Optional<Product> findById(Long id) {
        return executeEntityManager(em -> Optional.ofNullable(em.find(Product.class, id)));
    }

    public void deleteById(Long id) {
        executeTransaction(em -> em.createQuery("delete from Product where id = :id")
                .setParameter("id", id).executeUpdate());
    }

    public List findAll() {
        return executeEntityManager(em -> em.createNamedQuery("AllProducts")
                .getResultList());
    }

    public void findAllCostumers(Long id) {
        try {
            Product product = executeEntityManager(em -> em.createQuery("select p from Product p join fetch p.costumers where p.id = :id", Product.class)
                    .setParameter("id", id).getSingleResult());
            System.out.println(product.getName());
            System.out.println("was bought by:");
            product.getCostumers().forEach(System.out::println);
        } catch (NoResultException e) {
            System.out.println("Nobody bought " + findById(id));
        }
    }


    public void update(Product product) {
        executeTransaction(em -> em.merge(product));
    }

    public void insert(Product product) {
        executeTransaction(em -> em.persist(product));
    }

    public void save(Product product) {
        if (product.getId() == null) {
            insert(product);
        } else {
            update(product);
        }
    }

    private <R> R executeEntityManager(Function<EntityManager, R> function) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return function.apply(em);
        } finally {
            em.close();
        }
    }

    public void executeTransaction(Consumer<EntityManager> consumer) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            consumer.accept(em);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}