package com.ecommerce.magnik.controller;

import com.ecommerce.magnik.model.Category;
import com.ecommerce.magnik.payload.CategoryDTO;
import com.ecommerce.magnik.payload.CategoryResponse;
import com.ecommerce.magnik.service.CategoryService;
import com.ecommerce.magnik.config.AppConstant;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService cs;


    public CategoryController(CategoryService cs) {
        this.cs = cs;
    }

    @GetMapping("/echo")
    public ResponseEntity<String> echoMessage(@RequestParam(name="message",defaultValue = "Hello World!")String message)
    {
        return new ResponseEntity<>("Echoed message: "+message,HttpStatus.OK);
    }

    //@RequestMapping(value="/api/public/categories",method=RequestMethod.GET)
    @PostMapping("/public/categories")
    public ResponseEntity<String> createCategories(@Valid @RequestBody Category category) {
        try {
            cs.createCategory(category);
            return new ResponseEntity<>("Created category successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception if needed
            return new ResponseEntity<>("Failed to create category. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber,
                                                             @RequestParam(name = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize){
        CategoryResponse CategoryResponse = cs.getAllCategories(pageNumber,pageSize);
        return new ResponseEntity<>(CategoryResponse, HttpStatus.OK);
     }

     @DeleteMapping("/public/categories/{category_id}")
     public ResponseEntity<CategoryDTO>  DeleteCategory(@PathVariable Long category_id)
     {
         CategoryDTO deletedCategory=cs.DeleteCategory(category_id);
         return new ResponseEntity<>(deletedCategory,HttpStatus.OK);
     }


     @PutMapping("/public/categories/{category_id}")
     public ResponseEntity<CategoryDTO> UpdateCategories(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long category_id)
     {
//         try {
//             CategoryDTO updatedCategory=cs.UpdateCategory(CategoryDTO, category_id);
//
//             return new ResponseEntity<>("Updated category successfully", HttpStatus.OK);
//         } catch (Exception e) {
//             return new ResponseEntity<>("Failed to update category. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//         }
////         return "Updated category successfully";

         CategoryDTO savedCategoryDTO = cs.UpdateCategory(categoryDTO, category_id);
         return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
     }
}