package org.kfe.api.demo.dto;

import org.springframework.web.multipart.MultipartFile;

// DTO para actualizar un producto
public class UpdateProductRequestDTO {

    private String name;
    private Double price;
    private String description;
    private Long categoryId;
    private Integer stock;
    private MultipartFile image;   // si se envía, reemplaza la imagen actual

    // GETTERS
    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public Long getCategoryId(){
        return categoryId;
    }

    public Integer getStock(){
        return stock;
    }

    public MultipartFile getImage(){
        return image;
    }

    // SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

}