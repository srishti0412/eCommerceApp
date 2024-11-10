package com.ecommerce.magnik.service;

import com.ecommerce.magnik.model.Product;
import com.ecommerce.magnik.payload.ProductDTO;
import com.ecommerce.magnik.payload.ProductResponse;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);
    ProductResponse getAllProducts();
    ProductResponse searchByCategory(Long categoryId);
    ProductResponse searchProductByKeyword(String keyword);
    ProductDTO updateProduct(Long productId,Product product);
    ProductDTO deleteProduct(Long productId);
}
