package org.kfe.api.demo.service.impl;

import org.kfe.api.demo.dto.ProductVariantRequestDTO;
import org.kfe.api.demo.entity.Product;
import org.kfe.api.demo.entity.ProductVariant;
import org.kfe.api.demo.exception.ProductoNoEncontradoException;
import org.kfe.api.demo.repository.ProductRepository;
import org.kfe.api.demo.repository.ProductVariantRepository;
import org.kfe.api.demo.service.ProductVariantService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository variantRepository;
    private final ProductRepository productRepository;

    public ProductVariantServiceImpl(ProductVariantRepository variantRepository,
                                     ProductRepository productRepository) {
        this.variantRepository = variantRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ProductVariant addVariant(Long productId, ProductVariantRequestDTO request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductoNoEncontradoException("ID: " + productId));

        ProductVariant variant = new ProductVariant();
        variant.setProduct(product);
        variant.setSize(request.getSize());
        variant.setPrice(request.getPrice());
        variant.setStock(request.getStock());

        return variantRepository.save(variant);
    }

    @Override
    public List<ProductVariant> getVariantsByProduct(Long productId) {
        return variantRepository.findByProductId(productId);
    }

    @Override
    public ProductVariant updateVariant(Long variantId, ProductVariantRequestDTO request) {
        ProductVariant variant = variantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("Presentación no encontrada con ID: " + variantId));

        if (request.getSize() != null && !request.getSize().isBlank()) {
            variant.setSize(request.getSize());
        }
        if (request.getPrice() != null) {
            variant.setPrice(request.getPrice());
        }
        if (request.getStock() != null) {
            variant.setStock(request.getStock());
        }

        return variantRepository.save(variant);
    }

    @Override
    public void deleteVariant(Long variantId) {
        if (!variantRepository.existsById(variantId)) {
            throw new RuntimeException("Presentación no encontrada con ID: " + variantId);
        }
        variantRepository.deleteById(variantId);
    }
}