package org.kfe.api.demo.service;

import org.kfe.api.demo.dto.*;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleService {

    // Crear
    SaleResponseDTO create(CreateSaleRequestDTO request);

    // Cancelar
    SaleResponseDTO cancel(Long saleId, Long canceladoPorId);

    // Ventas por cajero
    List<SaleResponseDTO> findByCajero(Long cajeroId);

    // Ventas canceladas
    List<SaleResponseDTO> findCancelled();

    // Estadísticas por cajero
    List<CajeroSalesStatsDTO> getSalesByCajero();

    // Top 3 productos
    List<ProductSalesStatsDTO> getTop3Products();

    // Datos para gráfica de productos
    List<ProductSalesStatsDTO> getSalesChartData();

    // Ventas por período
    List<ProductSalesStatsDTO> getSalesByProductInPeriod(LocalDateTime start, LocalDateTime end);

    // Gráfica por día
    List<SalesChartDataDTO> getChartByDay(LocalDateTime start, LocalDateTime end);

    // Gráfica por mes
    List<SalesChartDataDTO> getChartByMonth(int year);

    // Gráfica por año
    List<SalesChartDataDTO> getChartByYear();
}