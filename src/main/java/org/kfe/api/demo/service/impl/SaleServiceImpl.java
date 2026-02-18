package org.kfe.api.demo.service.impl;

import org.kfe.api.demo.dto.*;
import org.kfe.api.demo.entity.*;
import org.kfe.api.demo.exception.ProductoNoEncontradoException;
import org.kfe.api.demo.exception.UsuarioNoEncontradoException;
import org.kfe.api.demo.repository.ProductRepository;
import org.kfe.api.demo.repository.SaleRepository;
import org.kfe.api.demo.repository.UserRepository;
import org.kfe.api.demo.service.SaleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private static final double IVA_RATE = 0.16; // 16%

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public SaleServiceImpl(SaleRepository saleRepository,
                           ProductRepository productRepository,
                           UserRepository userRepository) {
        this.saleRepository = saleRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Crear
    @Override
    @Transactional
    public SaleResponseDTO create(CreateSaleRequestDTO request) {

        User cajero = userRepository.findById(request.getCajeroId())
                .orElseThrow(() -> new UsuarioNoEncontradoException("Cajero ID: " + request.getCajeroId()));

        Sale sale = new Sale();
        sale.setCajero(cajero);
        sale.setStatus(SaleStatus.COMPLETADA);

        double totalSubtotal = 0.0;
        double totalIva      = 0.0;

        for (SaleItemDTO item : request.getItems()) {

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ProductoNoEncontradoException("ID: " + item.getProductId()));

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException(
                        "Stock insuficiente para '" + product.getName() +
                                "'. Disponible: " + product.getStock() +
                                ", solicitado: " + item.getQuantity());
            }

            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);

            double unitPrice     = product.getPrice();
            double subtotalLinea = unitPrice * item.getQuantity();
            double ivaLinea      = subtotalLinea * IVA_RATE;
            double totalLinea    = subtotalLinea + ivaLinea;

            SaleDetail detail = new SaleDetail();
            detail.setSale(sale);
            detail.setProduct(product);
            detail.setQuantity(item.getQuantity());
            detail.setUnitPrice(unitPrice);
            detail.setSubtotal(subtotalLinea);
            detail.setIva(ivaLinea);
            detail.setTotal(totalLinea);

            sale.getDetails().add(detail);
            totalSubtotal += subtotalLinea;
            totalIva      += ivaLinea;
        }

        sale.setSubtotal(totalSubtotal);
        sale.setIva(totalIva);
        sale.setTotal(totalSubtotal + totalIva);

        return new SaleResponseDTO(saleRepository.save(sale));
    }

    // Cancelar
    @Override
    @Transactional
    public SaleResponseDTO cancel(Long saleId, Long canceladoPorId) {

        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + saleId));

        if (sale.getStatus() == SaleStatus.CANCELADA) {
            throw new RuntimeException("La venta " + saleId + " ya está cancelada.");
        }

        User canceladoPor = userRepository.findById(canceladoPorId)
                .orElseThrow(() -> new UsuarioNoEncontradoException("ID: " + canceladoPorId));

        for (SaleDetail detail : sale.getDetails()) {
            Product p = detail.getProduct();
            p.setStock(p.getStock() + detail.getQuantity());
            productRepository.save(p);
        }

        sale.setStatus(SaleStatus.CANCELADA);
        sale.setCanceladoPor(canceladoPor);
        sale.setFechaCancelacion(LocalDateTime.now());

        return new SaleResponseDTO(saleRepository.save(sale));
    }

    // Ventas por cajero
    @Override
    public List<SaleResponseDTO> findByCajero(Long cajeroId) {
        return saleRepository.findByCajeroId(cajeroId)
                .stream().map(SaleResponseDTO::new).collect(Collectors.toList());
    }

    // Ventas canceladas
    @Override
    public List<SaleResponseDTO> findCancelled() {
        return saleRepository.findByStatus(SaleStatus.CANCELADA)
                .stream().map(SaleResponseDTO::new).collect(Collectors.toList());
    }

    // Estadísticas por cajero
    @Override
    public List<CajeroSalesStatsDTO> getSalesByCajero() {
        List<Sale> completadas = saleRepository.findByStatus(SaleStatus.COMPLETADA);
        Map<Long, CajeroSalesStatsDTO> mapa = new LinkedHashMap<>();

        for (Sale sale : completadas) {
            Long   cajeroId = sale.getCajero().getId();
            String nombre   = sale.getCajero().getUsername();
            Double total    = sale.getTotal();

            if (mapa.containsKey(cajeroId)) {
                mapa.compute(cajeroId, (k, s) -> new CajeroSalesStatsDTO(
                        cajeroId, nombre,
                        s.getTotalVentas() + 1,
                        s.getTotalIngresos() + total));
            } else {
                mapa.put(cajeroId, new CajeroSalesStatsDTO(cajeroId, nombre, 1L, total));
            }
        }

        return mapa.values().stream()
                .sorted(Comparator.comparingLong(CajeroSalesStatsDTO::getTotalVentas).reversed())
                .collect(Collectors.toList());
    }

    // Top 3 productos
    @Override
    public List<ProductSalesStatsDTO> getTop3Products() {
        return buildProductStats(saleRepository.findByStatus(SaleStatus.COMPLETADA))
                .stream()
                .sorted(Comparator.comparingLong(ProductSalesStatsDTO::getTotalVendido).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    // Datos para gráfica de productos
    @Override
    public List<ProductSalesStatsDTO> getSalesChartData() {
        return buildProductStats(saleRepository.findByStatus(SaleStatus.COMPLETADA))
                .stream()
                .sorted(Comparator.comparingLong(ProductSalesStatsDTO::getTotalVendido).reversed())
                .collect(Collectors.toList());
    }

    // Ventas por período
    @Override
    public List<ProductSalesStatsDTO> getSalesByProductInPeriod(LocalDateTime start, LocalDateTime end) {
        return buildProductStats(
                saleRepository.findByCreatedAtBetweenAndStatus(start, end, SaleStatus.COMPLETADA))
                .stream()
                .sorted(Comparator.comparingLong(ProductSalesStatsDTO::getTotalVendido).reversed())
                .collect(Collectors.toList());
    }

    // Gráfica por día — agrupa ventas de un rango por cada día
    // Ejemplo: start=2024-01-01, end=2024-01-31 → 31 puntos en la gráfica
    @Override
    public List<SalesChartDataDTO> getChartByDay(LocalDateTime start, LocalDateTime end) {
        List<Sale> ventas = saleRepository.findByCreatedAtBetweenAndStatus(start, end, SaleStatus.COMPLETADA);
        Map<String, double[]> mapa = new TreeMap<>(); // TreeMap para ordenar por fecha

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (Sale sale : ventas) {
            String dia = sale.getCreatedAt().format(fmt);
            mapa.merge(dia, new double[]{sale.getSubtotal(), sale.getIva(), sale.getTotal(), 1},
                    (a, b) -> new double[]{a[0] + b[0], a[1] + b[1], a[2] + b[2], a[3] + b[3]});
        }

        return mapa.entrySet().stream()
                .map(e -> new SalesChartDataDTO(
                        e.getKey(),
                        e.getValue()[0],
                        e.getValue()[1],
                        e.getValue()[2],
                        (long) e.getValue()[3]))
                .collect(Collectors.toList());
    }

    // Gráfica por mes — agrupa ventas de un año por cada mes (Enero a Diciembre)
    // Ejemplo: year=2024 → hasta 12 puntos en la gráfica
    @Override
    public List<SalesChartDataDTO> getChartByMonth(int year) {
        LocalDateTime start = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime end   = LocalDate.of(year, 12, 31).atTime(23, 59, 59);

        List<Sale> ventas = saleRepository.findByCreatedAtBetweenAndStatus(start, end, SaleStatus.COMPLETADA);
        Map<String, double[]> mapa = new TreeMap<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM");

        for (Sale sale : ventas) {
            String mes = sale.getCreatedAt().format(fmt);
            mapa.merge(mes, new double[]{sale.getSubtotal(), sale.getIva(), sale.getTotal(), 1},
                    (a, b) -> new double[]{a[0] + b[0], a[1] + b[1], a[2] + b[2], a[3] + b[3]});
        }

        return mapa.entrySet().stream()
                .map(e -> new SalesChartDataDTO(
                        e.getKey(),
                        e.getValue()[0],
                        e.getValue()[1],
                        e.getValue()[2],
                        (long) e.getValue()[3]))
                .collect(Collectors.toList());
    }

    // Gráfica por año — agrupa todas las ventas por año
    // Ejemplo: si tienes ventas de 2023, 2024 y 2025 → 3 puntos en la gráfica
    @Override
    public List<SalesChartDataDTO> getChartByYear() {
        List<Sale> ventas = saleRepository.findByStatus(SaleStatus.COMPLETADA);
        Map<String, double[]> mapa = new TreeMap<>();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy");

        for (Sale sale : ventas) {
            String anio = sale.getCreatedAt().format(fmt);
            mapa.merge(anio, new double[]{sale.getSubtotal(), sale.getIva(), sale.getTotal(), 1},
                    (a, b) -> new double[]{a[0] + b[0], a[1] + b[1], a[2] + b[2], a[3] + b[3]});
        }

        return mapa.entrySet().stream()
                .map(e -> new SalesChartDataDTO(
                        e.getKey(),
                        e.getValue()[0],
                        e.getValue()[1],
                        e.getValue()[2],
                        (long) e.getValue()[3]))
                .collect(Collectors.toList());
    }

    // Agrupa detalles de ventas por producto
    private List<ProductSalesStatsDTO> buildProductStats(List<Sale> ventas) {
        Map<Long, ProductSalesStatsDTO> mapa = new LinkedHashMap<>();

        for (Sale sale : ventas) {
            for (SaleDetail detail : sale.getDetails()) {
                Long   productId   = detail.getProduct().getId();
                String productName = detail.getProduct().getName();
                long   qty         = detail.getQuantity();
                double totalLinea  = detail.getTotal();

                if (mapa.containsKey(productId)) {
                    mapa.compute(productId, (k, s) -> new ProductSalesStatsDTO(
                            productId, productName,
                            s.getTotalVendido() + qty,
                            s.getTotalIngresos() + totalLinea));
                } else {
                    mapa.put(productId, new ProductSalesStatsDTO(
                            productId, productName, qty, totalLinea));
                }
            }
        }

        return new ArrayList<>(mapa.values());
    }
}