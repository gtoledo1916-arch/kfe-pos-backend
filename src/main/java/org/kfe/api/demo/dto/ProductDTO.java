package org.kfe.api.demo.dto;

public class ProductDTO {

    private Long id;
    private String name;
    private Double price;
    private String description;
    private String imageUrl;
    private String category;
    private Integer stock;
    private boolean lowStock;

    public ProductDTO(Long id, String name, Double price, String description,
                      String imageUrl, String category, Integer stock) {

        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.stock = stock;
        this.lowStock = stock <= 5;
    }

    //GETTERS
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getStock() {
        return stock;
    }

    public boolean isLowStock() {
        return lowStock;
    }
}
