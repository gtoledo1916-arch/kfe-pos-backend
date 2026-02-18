package org.kfe.api.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sale_details")
public class SaleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double iva;

    @Column(nullable = false)
    private Double total;

    // GETTERS
    public Long getId()          { return id; }
    public Sale getSale()        { return sale; }
    public Product getProduct()  { return product; }
    public Integer getQuantity() { return quantity; }
    public Double getUnitPrice() { return unitPrice; }
    public Double getSubtotal()  { return subtotal; }
    public Double getIva()       { return iva; }
    public Double getTotal()     { return total; }

    // SETTERS
    public void setId(Long id)              { this.id = id; }
    public void setSale(Sale sale)          { this.sale = sale; }
    public void setProduct(Product product) { this.product = product; }
    public void setQuantity(Integer quantity)   { this.quantity = quantity; }
    public void setUnitPrice(Double unitPrice)  { this.unitPrice = unitPrice; }
    public void setSubtotal(Double subtotal)    { this.subtotal = subtotal; }
    public void setIva(Double iva)              { this.iva = iva; }
    public void setTotal(Double total)          { this.total = total; }
}