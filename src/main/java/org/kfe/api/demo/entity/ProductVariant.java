package org.kfe.api.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "product_variants")
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Chico, Mediano, Grande, etc.
    @Column(nullable = false)
    private String size;

    // Precio de esta presentación
    @Column(nullable = false)
    private Double price;

    // Stock de esta presentación
    @Column(nullable = false)
    private Integer stock;

    // GETTERS
    public Long getId()          { return id; }
    public Product getProduct()  { return product; }
    public String getSize()      { return size; }
    public Double getPrice()     { return price; }
    public Integer getStock()    { return stock; }

    // SETTERS
    public void setId(Long id)              { this.id = id; }
    public void setProduct(Product product) { this.product = product; }
    public void setSize(String size)        { this.size = size; }
    public void setPrice(Double price)      { this.price = price; }
    public void setStock(Integer stock)     { this.stock = stock; }
}