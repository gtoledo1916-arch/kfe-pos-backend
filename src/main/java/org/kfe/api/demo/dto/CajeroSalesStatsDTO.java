package org.kfe.api.demo.dto;

public class CajeroSalesStatsDTO {

    private Long cajeroId;
    private String cajeroNombre;
    private Long totalVentas;
    private Double totalIngresos;

    public CajeroSalesStatsDTO(Long cajeroId, String cajeroNombre,
                               Long totalVentas, Double totalIngresos) {
        this.cajeroId      = cajeroId;
        this.cajeroNombre  = cajeroNombre;
        this.totalVentas   = totalVentas;
        this.totalIngresos = totalIngresos;
    }

    public Long getCajeroId()          { return cajeroId; }
    public String getCajeroNombre()    { return cajeroNombre; }
    public Long getTotalVentas()       { return totalVentas; }
    public Double getTotalIngresos()   { return totalIngresos; }

}
