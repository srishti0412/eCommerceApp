package com.ecommerce.magnik.service;

import com.ecommerce.magnik.exception.ApiException;
import com.ecommerce.magnik.exception.ResourceNotFoundException;
import com.ecommerce.magnik.model.Category;
import com.ecommerce.magnik.payload.CategoryDTO;
import com.ecommerce.magnik.payload.CategoryResponse;
import com.ecommerce.magnik.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    //private ArrayList<Category> categories=new ArrayList<>();

    private Long nextId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(@RequestParam(name = "pageNumber") Integer pageNumber,
                                             @RequestParam(name = "pageSize") Integer pageSize){

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categories = categoryPage.getContent();

        //List<Category>categories=categoryRepository.findAll();
        if (categories.isEmpty())
            throw new ApiException("No category created till now.");

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
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
    public CategoryDTO DeleteCategory(Long categoryId) {

        // Fetch category by ID and delete if it exists
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));


//        Category category=modelMapper.map(categoryDTO,Category.class);
        categoryRepository.delete(category);

        return modelMapper.map(category,CategoryDTO.class);

    }
//
    @Override
    public CategoryDTO UpdateCategory(CategoryDTO categoryDTO, Long category_id)
    {
        Category existingCategory = categoryRepository.findById(category_id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", category_id));

        Category category=modelMapper.map(categoryDTO,Category.class);
        category.setCategoryId(category_id);


        //existingCategory.setCategoryName(updatedcategory.getCategoryName());

        existingCategory=categoryRepository.save(category);
        return modelMapper.map(existingCategory,CategoryDTO.class);
    }
}
