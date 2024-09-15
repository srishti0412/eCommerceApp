package com.ecommerce.magnik;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService cs;


    public CategoryController(CategoryService cs) {
        this.cs = cs;
    }

    //@RequestMapping(value="/api/public/categories",method=RequestMethod.GET)
    @PostMapping("/api/public/categories")
    public ResponseEntity<String> createCategories(@Valid @RequestBody Category category) {
        try {
            cs.createCategory(category);
            return new ResponseEntity<>("Created category successfully!", HttpStatus.CREATED);
        } catch (Exception e) {
            // Log the exception if needed
            return new ResponseEntity<>("Failed to create category. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(){
        CategoryResponse CategoryResponse = cs.getAllCategories();
        return new ResponseEntity<>(CategoryResponse, HttpStatus.OK);
     }

     @DeleteMapping("/api/public/categories/{category_id}")
     public ResponseEntity<String>  DeleteCategory(@PathVariable Long category_id)
     {
         String msg=cs.DeleteCategory(category_id);
         return new ResponseEntity<>(msg,HttpStatus.OK);
     }

     @PutMapping("/api/public/categories/{category_id}")
     public ResponseEntity<String> UpdateCategories(@RequestBody Category updatedcategory,@PathVariable Long category_id)
     {
         try {
             Category savedCategory=cs.UpdateCategory(updatedcategory, category_id);

             return new ResponseEntity<>("Updated category successfully", HttpStatus.OK);
         } catch (Exception e) {
             return new ResponseEntity<>("Failed to update category. " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
         }
//         return "Updated category successfully";
     }
}