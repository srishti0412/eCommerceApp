package com.ecommerce.magnik.service;

import com.ecommerce.magnik.model.Category;
import com.ecommerce.magnik.payload.CategoryDTO;
import com.ecommerce.magnik.payload.CategoryResponse;

public interface CategoryService {
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize);
    public void createCategory(Category category);
    public CategoryDTO DeleteCategory(Long category_id);
    public CategoryDTO UpdateCategory(CategoryDTO categoryDTO, Long category_id);
}
