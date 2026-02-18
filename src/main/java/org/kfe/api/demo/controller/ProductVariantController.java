package org.kfe.api.demo.controller;

import org.kfe.api.demo.dto.ProductVariantRequestDTO;
import org.kfe.api.demo.entity.ProductVariant;
import org.kfe.api.demo.service.ProductVariantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/variants")
public class ProductVariantController {

    private final ProductVariantService variantService;

    public ProductVariantController(ProductVariantService variantService) {
        this.variantService = variantService;
    }

    // POST /api/products/{productId}/variants
    // 201 Created | 404 producto no existe
    @PostMapping
    public ResponseEntity<ProductVariant> addVariant(
            @PathVariable Long productId,
            @RequestBody ProductVariantRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(variantService.addVariant(productId, request));
    }

    // GET /api/products/{productId}/variants
    // 200 OK | 204 sin variantes
    @GetMapping
    public ResponseEntity<List<ProductVariant>> getVariants(@PathVariable Long productId) {
        List<ProductVariant> list = variantService.getVariantsByProduct(productId);
        if (list.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(list);
    }

    // PUT /api/products/{productId}/variants/{variantId}
    // 200 OK | 404 variante no existe
    @PutMapping("/{variantId}")
    public ResponseEntity<ProductVariant> updateVariant(
            @PathVariable Long productId,
            @PathVariable Long variantId,
            @RequestBody ProductVariantRequestDTO request) {
        return ResponseEntity.ok(variantService.updateVariant(variantId, request));
    }

    // DELETE /api/products/{productId}/variants/{variantId}
    // 204 No Content | 404 variante no existe
    @DeleteMapping("/{variantId}")
    public ResponseEntity<Void> deleteVariant(
            @PathVariable Long productId,
            @PathVariable Long variantId) {
        variantService.deleteVariant(variantId);
        return ResponseEntity.noContent().build();
    }
}