package com.ecommerce.magnik;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements  CategoryService{

    //private ArrayList<Category> categories=new ArrayList<>();

    private Long nextId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(){

        List<Category>categories=categoryRepository.findAll();
        if (categories.isEmpty())
            throw new ApiException("No category created till now.");

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        return categoryResponse;
    }



    @Override
    public void createCategory(Category category) {
        // Check if a category with the same name already exists
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory != null) {
            throw new ApiException("Category with the name " + category.getCategoryName() + " already exists !!!");
        }

        // No need to manually set category_id, the database will handle this

        categoryRepository.save(category);
    }

    @Override
    public String DeleteCategory(Long categoryId) {

        // Fetch category by ID and delete if it exists
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        categoryRepository.delete(category);
        return "Category with id " + categoryId + " deleted successfully!";

    }
//
    @Override
    public void UpdateCategory(Category updatedcategory, Long category_id)
    {
//        List<Category>categories=categoryRepository.findAll();
//
//        Category ct = categories.stream().filter(c->c.getCategoryId()==(category_id)).findFirst()
//                .orElseThrow(()->new ResourceNotFoundException("Category","categoryId",category_id));
//
//        //System.out.println("CT info: "+ct.get().getCategory_name()+" , "+ct.get().getCategory_id());
//        ct.setCategoryName(updatedcategory.getCategoryName());
//
//        categoryRepository.save(ct);

        Category existingCategory = categoryRepository.findById(category_id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", category_id));

        existingCategory.setCategoryName(updatedcategory.getCategoryName());

        categoryRepository.save(existingCategory);
//       return ct;
    }
}
