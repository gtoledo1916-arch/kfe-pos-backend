package org.kfe.api.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cajero_id", nullable = false)
    private User cajero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SaleStatus status = SaleStatus.COMPLETADA;

    @ManyToOne
    @JoinColumn(name = "cancelado_por_id")
    private User canceladoPor;

    @Column(name = "fecha_cancelacion")
    private LocalDateTime fechaCancelacion;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Suma de subtotales sin IVA
    @Column(nullable = false)
    private Double subtotal = 0.0;

    // Suma de IVA de todos los productos (16%)
    @Column(nullable = false)
    private Double iva = 0.0;

    // Total final (subtotal + iva)
    @Column(nullable = false)
    private Double total = 0.0;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SaleDetail> details = new ArrayList<>();

    // GETTERS
    public Long getId()                        { return id; }
    public User getCajero()                    { return cajero; }
    public SaleStatus getStatus()              { return status; }
    public User getCanceladoPor()              { return canceladoPor; }
    public LocalDateTime getFechaCancelacion() { return fechaCancelacion; }
    public LocalDateTime getCreatedAt()        { return createdAt; }
    public Double getSubtotal()                { return subtotal; }
    public Double getIva()                     { return iva; }
    public Double getTotal()                   { return total; }
    public List<SaleDetail> getDetails()       { return details; }

    // SETTERS
    public void setId(Long id)                                      { this.id = id; }
    public void setCajero(User cajero)                              { this.cajero = cajero; }
    public void setStatus(SaleStatus status)                        { this.status = status; }
    public void setCanceladoPor(User canceladoPor)                  { this.canceladoPor = canceladoPor; }
    public void setFechaCancelacion(LocalDateTime fechaCancelacion) { this.fechaCancelacion = fechaCancelacion; }
    public void setCreatedAt(LocalDateTime createdAt)               { this.createdAt = createdAt; }
    public void setSubtotal(Double subtotal)                        { this.subtotal = subtotal; }
    public void setIva(Double iva)                                  { this.iva = iva; }
    public void setTotal(Double total)                              { this.total = total; }
    public void setDetails(List<SaleDetail> details)                { this.details = details; }
}