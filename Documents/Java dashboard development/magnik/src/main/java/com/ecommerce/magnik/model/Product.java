package com.ecommerce.magnik.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="product")
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Column(nullable=false)
    @NotBlank
    @Size(min = 3, message = "Product name must contain atleast 3 characters")
    private String productName;

    private String image;

    @Column(nullable=false)
    @NotBlank
    @Size(min = 6, message = "Product description must contain atleast 6 characters")
    private String description;

    @Column(nullable=false)
    private Integer quantity;

    @Column(nullable=false)
    private double price;

    @Column(nullable=false)
    private double discount;

    @Column(nullable=false)
    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name="seller_id")
    private User user;
}
