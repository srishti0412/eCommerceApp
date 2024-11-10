package com.ecommerce.magnik.controller;

import com.ecommerce.magnik.model.Product;
import com.ecommerce.magnik.payload.ProductDTO;
import com.ecommerce.magnik.payload.ProductResponse;
import com.ecommerce.magnik.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO prodDTO,
                                                 @PathVariable Long categoryId) {
        ProductDTO productDTO = productService.addProduct(categoryId, prodDTO);
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts() {
        ProductResponse productResponse = productService.getAllProducts();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> searchByCategory(@PathVariable Long categoryId) {
        ProductResponse productResponse = productService.searchByCategory(categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword) {
        ProductResponse productResponse = productService.searchProductByKeyword(keyword);
        return new ResponseEntity<>(productResponse, HttpStatus.FOUND);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId,@RequestBody Product product)
    {
        ProductDTO productDTO = productService.updateProduct(productId,product);
        return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId)
    {
        ProductDTO productDTO=productService.deleteProduct(productId);
        return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }

}