package ru.gb;

import ru.gb.tables.Costumer;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Component("costumerDao")
public class CostumerDao {

    private EntityManagerFactory emf;

    public CostumerDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Optional<Costumer> findById(Long id) {
        return executeEntityManager(em -> Optional.ofNullable(em.find(Costumer.class, id)));
    }

    public void deleteById(Long id) {
        executeTransaction(em -> em.createQuery("delete from Costumer where id = :id")
                .setParameter("id", id).executeUpdate());
    }

    public List findAll() {
        return executeEntityManager(em -> em.createNamedQuery("AllCostumers")
                .getResultList());
    }

    public void update(Costumer costumer) {
        executeTransaction(em -> em.merge(costumer));
    }

    public void insert(Costumer costumer) {
        executeTransaction(em -> em.persist(costumer));
    }

    public void save(Costumer costumer) {
        if (costumer.getId() == null) {
            insert(costumer);
        } else {
            update(costumer);
        }
    }


    public void findAllProducts(Long id) {
        try {
            Costumer costumer = executeEntityManager(em -> em.createQuery("select c from Costumer c join fetch c.product where c.id = :id", Costumer.class)
                    .setParameter("id", id).getSingleResult());
            System.out.println(costumer.getName());
            System.out.println("has in cart:");
            costumer.getProduct().forEach(System.out::println);
        } catch (NoResultException e) {
            System.out.println(findById(id) + " has nothing in cart");
        }
    }

    private <R> R executeEntityManager(Function<EntityManager, R> function) {
        EntityManager em = emf.createEntityManager();
        try {
            return function.apply(em);
        } finally {
            em.close();
        }
    }

    public void executeTransaction(Consumer<EntityManager> consumer) {
        EntityManager em = emf.createEntityManager();
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
