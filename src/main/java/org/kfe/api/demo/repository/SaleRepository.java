package org.kfe.api.demo.repository;

import org.kfe.api.demo.entity.Sale;
import org.kfe.api.demo.entity.SaleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    // Ventas por cajero
    List<Sale> findByCajeroId(Long cajeroId);

    // Ventas por status (COMPLETADA / CANCELADA)
    List<Sale> findByStatus(SaleStatus status);

    // Ventas completadas en rango de fechas
    List<Sale> findByCreatedAtBetweenAndStatus(
            LocalDateTime start,
            LocalDateTime end,
            SaleStatus status);
}