package com.ecommerce.magnik.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @NotBlank
    @Size(min = 5, message = "Category name must contain atleast 5 characters")
    private String categoryName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // The ID will be auto-generated
    private Long categoryId;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

}
