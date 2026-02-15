package org.kfe.api.demo.controller;

import org.kfe.api.demo.entity.Product;
import org.kfe.api.demo.service.ProductService;
import org.kfe.api.demo.service.impl.ProductServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Product> create(
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam String description,
            @RequestParam Long categoryId,
            @RequestParam Integer stock,
            @RequestParam MultipartFile image
    ) throws IOException {
        return ResponseEntity.ok(
                productService.create(name, price, description, categoryId, stock, image)
        );
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Product>> byCategory(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findByCategory(id));
    }
}
