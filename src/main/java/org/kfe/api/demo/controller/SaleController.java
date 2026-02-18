package org.kfe.api.demo.controller;

import org.kfe.api.demo.dto.*;
import org.kfe.api.demo.service.SaleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    // POST /api/sales
    @PostMapping
    public ResponseEntity<SaleResponseDTO> create(@RequestBody CreateSaleRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.create(request));  // 201
    }

    // PATCH /api/sales/{id}/cancel?canceladoPorId=5
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<SaleResponseDTO> cancel(
            @PathVariable Long id,
            @RequestParam Long canceladoPorId) {
        return ResponseEntity.ok(saleService.cancel(id, canceladoPorId));  // 200
    }

    // GET /api/sales/cajero/{cajeroId}
    @GetMapping("/cajero/{cajeroId}")
    public ResponseEntity<List<SaleResponseDTO>> byCajero(@PathVariable Long cajeroId) {
        List<SaleResponseDTO> list = saleService.findByCajero(cajeroId);
        if (list.isEmpty()) return ResponseEntity.noContent().build();  // 204
        return ResponseEntity.ok(list);  // 200
    }

    // GET /api/sales/cancelled
    @GetMapping("/cancelled")
    public ResponseEntity<List<SaleResponseDTO>> cancelled() {
        List<SaleResponseDTO> list = saleService.findCancelled();
        if (list.isEmpty()) return ResponseEntity.noContent().build();  // 204
        return ResponseEntity.ok(list);  // 200
    }

    // GET /api/sales/stats/cajeros
    @GetMapping("/stats/cajeros")
    public ResponseEntity<List<CajeroSalesStatsDTO>> statsByCajero() {
        List<CajeroSalesStatsDTO> list = saleService.getSalesByCajero();
        if (list.isEmpty()) return ResponseEntity.noContent().build();  // 204
        return ResponseEntity.ok(list);  // 200
    }

    // GET /api/sales/stats/top3
    @GetMapping("/stats/top3")
    public ResponseEntity<List<ProductSalesStatsDTO>> top3() {
        List<ProductSalesStatsDTO> list = saleService.getTop3Products();
        if (list.isEmpty()) return ResponseEntity.noContent().build();  // 204
        return ResponseEntity.ok(list);  // 200
    }

    // GET /api/sales/stats/chart
    @GetMapping("/stats/chart")
    public ResponseEntity<List<ProductSalesStatsDTO>> chart() {
        List<ProductSalesStatsDTO> list = saleService.getSalesChartData();
        if (list.isEmpty()) return ResponseEntity.noContent().build();  // 204
        return ResponseEntity.ok(list);  // 200
    }

    // GET /api/sales/stats/today
    @GetMapping("/stats/today")
    public ResponseEntity<List<ProductSalesStatsDTO>> today() {
        LocalDateTime from = LocalDate.now().atStartOfDay();
        LocalDateTime to   = LocalDate.now().atTime(23, 59, 59);
        List<ProductSalesStatsDTO> list = saleService.getSalesByProductInPeriod(from, to);
        if (list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    // GET /api/sales/stats/this-week
    @GetMapping("/stats/this-week")
    public ResponseEntity<List<ProductSalesStatsDTO>> thisWeek() {
        LocalDateTime from = LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay();
        LocalDateTime to   = LocalDate.now().with(DayOfWeek.SUNDAY).atTime(23, 59, 59);
        List<ProductSalesStatsDTO> list = saleService.getSalesByProductInPeriod(from, to);
        if (list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    // GET /api/sales/stats/this-month
    @GetMapping("/stats/this-month")
    public ResponseEntity<List<ProductSalesStatsDTO>> thisMonth() {
        LocalDate first = LocalDate.now().withDayOfMonth(1);
        LocalDate last  = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());
        LocalDateTime from = first.atStartOfDay();
        LocalDateTime to   = last.atTime(23, 59, 59);
        List<ProductSalesStatsDTO> list = saleService.getSalesByProductInPeriod(from, to);
        if (list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    // GET /api/sales/stats/period/2024 <- por ejemplo
    @GetMapping("/stats/period/{year}")
    public ResponseEntity<List<ProductSalesStatsDTO>> byYear(@PathVariable int year) {

        LocalDateTime from = LocalDate.of(year, 1, 1).atStartOfDay();       // 2024-01-01T00:00:00
        LocalDateTime to   = LocalDate.of(year, 12, 31).atTime(23, 59, 59); // 2024-12-31T23:59:59

        List<ProductSalesStatsDTO> list = saleService.getSalesByProductInPeriod(from, to);
        if (list.isEmpty()) return ResponseEntity.noContent().build();  // 204
        return ResponseEntity.ok(list);  // 200
    }
}