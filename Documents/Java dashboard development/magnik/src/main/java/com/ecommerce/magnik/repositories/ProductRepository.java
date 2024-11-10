package com.ecommerce.magnik.repositories;

import com.ecommerce.magnik.model.Category;
import com.ecommerce.magnik.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository  extends JpaRepository<Product,Long> {
    List<Product> findByCategoryOrderByPriceAsc(Category category);

    List<Product> findByProductNameLikeIgnoreCase(String s);
}
