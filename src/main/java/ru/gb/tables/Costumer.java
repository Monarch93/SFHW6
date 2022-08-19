package ru.gb.tables;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "costumers")
@NamedQueries({
        @NamedQuery(name = "AllCostumers", query = "select c from Costumer c")
})
public class Costumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 256, nullable = false)
    private String name;


    @ManyToMany
    @JoinTable(name = "costumer_product",
            joinColumns = @JoinColumn(name = "costumer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> product;

    public Costumer() {
    }

    public Costumer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return "Costumer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
