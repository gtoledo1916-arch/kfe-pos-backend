package org.kfe.api.demo.service.impl;

import org.kfe.api.demo.dto.UpdateProductRequestDTO;
import org.kfe.api.demo.entity.Category;
import org.kfe.api.demo.entity.Product;
import org.kfe.api.demo.exception.ProductoNoEncontradoException;
import org.kfe.api.demo.repository.CategoryRepository;
import org.kfe.api.demo.repository.ProductRepository;
import org.kfe.api.demo.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    // Crear
    @Override
    public Product create(String name, Double price, String description,
                          Long categoryId, Integer stock, MultipartFile image) throws IOException {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + categoryId));

        String fileName = saveImage(image);

        // Usa exactamente los setters que tiene tu Product.java
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setStock(stock);
        product.setImageUrl(fileName);        // → imageUrl
        product.setCategory(category);        // → category (objeto Category)

        return productRepository.save(product);
    }

    // Listar todos
    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Buscar por ID
    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("ID: " + id));
    }

    //  Buscar por nombre
    @Override
    public List<Product> findByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    // Buscar por categoría
    @Override
    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    //  Actualizar
    @Override
    public Product update(Long id, UpdateProductRequestDTO request) throws IOException {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException("ID: " + id));

        // Solo actualiza los campos que vengan (no null / no vacíos)
        if (request.getName() != null && !request.getName().isBlank()) {
            product.setName(request.getName());
        }
        if (request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            product.setDescription(request.getDescription());
        }
        if (request.getStock() != null) {
            product.setStock(request.getStock());
        }
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + request.getCategoryId()));
            product.setCategory(category);
        }
        if (request.getImage() != null && !request.getImage().isEmpty()) {
            product.setImageUrl(saveImage(request.getImage()));   // → imageUrl
        }

        return productRepository.save(product);
    }

    //  Eliminar
    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductoNoEncontradoException("ID: " + id);
        }
        productRepository.deleteById(id);
    }

    // Guardar imagen en disco
    private String saveImage(MultipartFile image) throws IOException {
        File folder = new File(UPLOAD_DIR);
        if (!folder.exists()) folder.mkdirs();

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }
}