package com.ecommerce.magnik;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity(name = "categories")
@Data
@AllArgsConstructor
public class Category {

    @NotBlank
    private String categoryName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // The ID will be auto-generated
    private Long categoryId;

    public Category() {
        // This constructor is needed by JPA
    }

}
