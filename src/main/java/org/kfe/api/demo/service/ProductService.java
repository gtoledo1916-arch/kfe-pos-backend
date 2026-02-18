package org.kfe.api.demo.service;

import org.kfe.api.demo.dto.UpdateProductRequestDTO;
import org.kfe.api.demo.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface ProductService {

    // Crear producto con imagen
    Product create(String name, Double price, String description,
                   Long categoryId, Integer stock, MultipartFile image) throws IOException;

    // Listar todos
    List<Product> findAll();

    // Buscar por ID
    Product findById(Long id);

    // Buscar por nombre
    List<Product> findByName(String name);

    // Buscar por categoría
    List<Product> findByCategory(Long categoryId);

    // Actualizar (solo los campos que vengan)
    Product update(Long id, UpdateProductRequestDTO request) throws IOException;

    // Eliminar
    void delete(Long id);
}
