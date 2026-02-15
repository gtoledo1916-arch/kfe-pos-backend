package org.kfe.api.demo.repository;

import org.kfe.api.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategoryId(Long categoryId);
}
