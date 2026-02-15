package org.kfe.api.demo.service;

import org.kfe.api.demo.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface ProductService {

    Product create(String name,
                   Double price,
                   String description,
                   Long categoryId,
                   Integer stock,
                   MultipartFile image) throws IOException;

    List<Product> findAll();

    List<Product> findByCategory(Long categoryId);
}
