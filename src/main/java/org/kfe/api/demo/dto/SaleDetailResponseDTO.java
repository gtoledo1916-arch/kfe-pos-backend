package org.kfe.api.demo.dto;

import org.kfe.api.demo.entity.SaleDetail;

public class SaleDetailResponseDTO {

    private Long productId;
    private String productName;
    private String productImageUrl;
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;
    private Double iva;
    private Double total;

    public SaleDetailResponseDTO(SaleDetail detail) {
        this.productId       = detail.getProduct().getId();
        this.productName     = detail.getProduct().getName();
        this.productImageUrl = detail.getProduct().getImageUrl();
        this.quantity        = detail.getQuantity();
        this.unitPrice       = detail.getUnitPrice();
        this.subtotal        = detail.getSubtotal();
        this.iva             = detail.getIva();
        this.total           = detail.getTotal();
    }

    // GETTERS
    public Long getProductId()          { return productId; }
    public String getProductName()      { return productName; }
    public String getProductImageUrl()  { return productImageUrl; }
    public Integer getQuantity()        { return quantity; }
    public Double getUnitPrice()        { return unitPrice; }
    public Double getSubtotal()         { return subtotal; }
    public Double getIva()              { return iva; }
    public Double getTotal()            { return total; }
}