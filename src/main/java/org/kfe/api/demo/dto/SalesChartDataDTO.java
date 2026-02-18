package org.kfe.api.demo.dto;

public class SalesChartDataDTO {

    private String label;        // "2024-01-15" / "2024-01" / "2024"
    private Double subtotal;     // sin IVA
    private Double iva;          // 16%
    private Double total;        // con IVA
    private Long totalVentas;    // número de ventas

    public SalesChartDataDTO(String label, Double subtotal, Double iva, Double total, Long totalVentas) {
        this.label       = label;
        this.subtotal    = subtotal;
        this.iva         = iva;
        this.total       = total;
        this.totalVentas = totalVentas;
    }

    // GETTERS
    public String getLabel()       { return label; }
    public Double getSubtotal()    { return subtotal; }
    public Double getIva()         { return iva; }
    public Double getTotal()       { return total; }
    public Long getTotalVentas()   { return totalVentas; }
}