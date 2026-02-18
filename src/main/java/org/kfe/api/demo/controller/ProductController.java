package org.kfe.api.demo.controller;

import org.kfe.api.demo.dto.UpdateProductRequestDTO;
import org.kfe.api.demo.entity.Product;
import org.kfe.api.demo.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // POST /api/products
    @PostMapping
    public ResponseEntity<Product> create(
            @RequestParam String name,
            @RequestParam Double price,
            @RequestParam String description,
            @RequestParam Long categoryId,
            @RequestParam Integer stock,
            @RequestParam MultipartFile image
    ) throws IOException {
        Product created = productService.create(name, price, description, categoryId, stock, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);   // 201
    }

    // GET /api/products
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> list = productService.findAll();
        if (list.isEmpty()) return ResponseEntity.noContent().build();    // 204
        return ResponseEntity.ok(list);                                   // 200
    }

    //  GET /api/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));            // 200
    }

    //  GET /api/products/search?name=cafe
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchByName(@RequestParam String name) {
        List<Product> results = productService.findByName(name);
        if (results.isEmpty()) return ResponseEntity.noContent().build(); // 204
        return ResponseEntity.ok(results);                                // 200
    }

    // GET /api/products/category/{id}
    @GetMapping("/category/{id}")
    public ResponseEntity<List<Product>> byCategory(@PathVariable Long id) {
        List<Product> results = productService.findByCategory(id);
        if (results.isEmpty()) return ResponseEntity.noContent().build(); // 204
        return ResponseEntity.ok(results);                                // 200
    }

    // PUT /api/products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double price,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Integer stock,
            @RequestParam(required = false) MultipartFile image
    ) throws IOException {
        UpdateProductRequestDTO request = new UpdateProductRequestDTO();
        request.setName(name);
        request.setPrice(price);
        request.setDescription(description);
        request.setCategoryId(categoryId);
        request.setStock(stock);
        request.setImage(image);

        return ResponseEntity.ok(productService.update(id, request));    // 200
    }

    // DELETE /api/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();                        // 204
    }
}