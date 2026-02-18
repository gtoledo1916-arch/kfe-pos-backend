package org.kfe.api.demo.dto;

import org.kfe.api.demo.entity.Sale;
import org.kfe.api.demo.entity.SaleStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SaleResponseDTO {

    private Long id;
    private Long cajeroId;
    private String cajeroNombre;
    private SaleStatus status;
    private Long canceladoPorId;
    private String canceladoPorNombre;
    private LocalDateTime fechaCancelacion;
    private LocalDateTime createdAt;
    private Double subtotal;   // sin IVA
    private Double iva;        // 16%
    private Double total;      // con IVA
    private List<SaleDetailResponseDTO> details;

    public SaleResponseDTO(Sale sale) {
        this.id               = sale.getId();
        this.cajeroId         = sale.getCajero().getId();
        this.cajeroNombre     = sale.getCajero().getUsername();
        this.status           = sale.getStatus();
        this.createdAt        = sale.getCreatedAt();
        this.subtotal         = sale.getSubtotal();
        this.iva              = sale.getIva();
        this.total            = sale.getTotal();
        this.fechaCancelacion = sale.getFechaCancelacion();

        if (sale.getCanceladoPor() != null) {
            this.canceladoPorId     = sale.getCanceladoPor().getId();
            this.canceladoPorNombre = sale.getCanceladoPor().getUsername();
        }

        this.details = sale.getDetails().stream()
                .map(SaleDetailResponseDTO::new)
                .collect(Collectors.toList());
    }

    // GETTERS
    public Long getId()                             { return id; }
    public Long getCajeroId()                       { return cajeroId; }
    public String getCajeroNombre()                 { return cajeroNombre; }
    public SaleStatus getStatus()                   { return status; }
    public Long getCanceladoPorId()                 { return canceladoPorId; }
    public String getCanceladoPorNombre()           { return canceladoPorNombre; }
    public LocalDateTime getFechaCancelacion()      { return fechaCancelacion; }
    public LocalDateTime getCreatedAt()             { return createdAt; }
    public Double getSubtotal()                     { return subtotal; }
    public Double getIva()                          { return iva; }
    public Double getTotal()                        { return total; }
    public List<SaleDetailResponseDTO> getDetails() { return details; }
}