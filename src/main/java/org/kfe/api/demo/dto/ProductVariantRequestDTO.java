package org.kfe.api.demo.dto;

public class ProductVariantRequestDTO {

    private String size;
    private Double price;
    private Integer stock;

    // GETTERS
    public String getSize()    { return size; }
    public Double getPrice()   { return price; }
    public Integer getStock()  { return stock; }

    // SETTERS
    public void setSize(String size)      { this.size = size; }
    public void setPrice(Double price)    { this.price = price; }
    public void setStock(Integer stock)   { this.stock = stock; }
}
