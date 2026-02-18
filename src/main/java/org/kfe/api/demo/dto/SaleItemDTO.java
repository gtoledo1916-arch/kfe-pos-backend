package org.kfe.api.demo.dto;

public class SaleItemDTO {

    private Long productId;
    private Integer quantity;

    // GETTERS
    public Long getProductId()    { return productId; }
    public Integer getQuantity()  { return quantity; }

    // SETTERS
    public void setProductId(Long productId)    { this.productId = productId; }
    public void setQuantity(Integer quantity)   { this.quantity = quantity; }
}