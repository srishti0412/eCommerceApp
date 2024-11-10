package com.ecommerce.magnik.repositories;

import com.ecommerce.magnik.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByCategoryName(String categoryName);
}
