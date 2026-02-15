package org.kfe.api.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String name;
    private Double price;
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    //GETTERS
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getStock() {
        return stock;
    }

    public Category getCategory() {
        return category;
    }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
