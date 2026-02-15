package org.kfe.api.demo.service.impl;

import org.kfe.api.demo.entity.Category;
import org.kfe.api.demo.entity.Product;
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

    @Override
    public Product create(String name, Double price, String description,
                          Long categoryId, Integer stock, MultipartFile image) throws IOException {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        File folder = new File(UPLOAD_DIR);
        if (!folder.exists()) folder.mkdirs();

        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();
        Path path = Paths.get(UPLOAD_DIR + fileName);
        Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setStock(stock);
        product.setImageUrl(fileName);
        product.setCategory(category);

        return productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}
