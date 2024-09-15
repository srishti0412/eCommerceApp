package com.ecommerce.magnik;

import java.util.List;

public interface CategoryService {
    public CategoryResponse getAllCategories();
    public void createCategory(Category category);
    public String DeleteCategory(Long category_id);
    public Category UpdateCategory(Category UpdatedCategory, Long category_id);
}
