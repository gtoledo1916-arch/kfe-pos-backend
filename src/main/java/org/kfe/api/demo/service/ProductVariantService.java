package org.kfe.api.demo.service;

import org.kfe.api.demo.dto.ProductVariantRequestDTO;
import org.kfe.api.demo.entity.ProductVariant;

import java.util.List;

public interface ProductVariantService {

    ProductVariant addVariant(Long productId, ProductVariantRequestDTO request);

    List<ProductVariant> getVariantsByProduct(Long productId);

    ProductVariant updateVariant(Long variantId, ProductVariantRequestDTO request);

    void deleteVariant(Long variantId);
}