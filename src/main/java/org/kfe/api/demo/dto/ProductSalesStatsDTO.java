package org.kfe.api.demo.dto;

public class ProductSalesStatsDTO {

    private Long productId;
    private String productName;
    private Long totalVendido;     // unidades vendidas
    private Double totalIngresos;  // dinero generado

    public ProductSalesStatsDTO(Long productId, String productName, Long totalVendido, Double totalIngresos) {

        this.productId     = productId;
        this.productName   = productName;
        this.totalVendido  = totalVendido;
        this.totalIngresos = totalIngresos;
    }

    public Long getProductId()         { return productId; }
    public String getProductName()     { return productName; }
    public Long getTotalVendido()      { return totalVendido; }
    public Double getTotalIngresos()   { return totalIngresos; }
}
