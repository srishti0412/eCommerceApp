package com.ecommerce.magnik;

import java.util.List;

public interface CategoryService {
    public CategoryResponse getAllCategories(Integer pageNumber,Integer pageSize);
    public void createCategory(Category category);
    public CategoryDTO DeleteCategory(Long category_id);
    public CategoryDTO UpdateCategory(CategoryDTO categoryDTO, Long category_id);
}
