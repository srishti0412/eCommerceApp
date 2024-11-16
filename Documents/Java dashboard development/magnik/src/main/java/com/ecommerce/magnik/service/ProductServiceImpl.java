package com.ecommerce.magnik.service;

import com.ecommerce.magnik.exception.ApiException;
import com.ecommerce.magnik.exception.ResourceNotFoundException;
import com.ecommerce.magnik.model.Cart;
import com.ecommerce.magnik.model.Category;
import com.ecommerce.magnik.model.Product;
import com.ecommerce.magnik.payload.CartDTO;
import com.ecommerce.magnik.payload.ProductDTO;
import com.ecommerce.magnik.payload.ProductResponse;
import com.ecommerce.magnik.repositories.CartRepository;
import com.ecommerce.magnik.repositories.CategoryRepository;
import com.ecommerce.magnik.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CartService cartService;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category","categoryId",categoryId));

        boolean Productnotavailable=true;

        List<Product>products=category.getProducts();
        for(Product value:products)
        {
          if(value.getProductName().equals(productDTO.getProductName()))
            {
                Productnotavailable = false;
                break;
            }
        }

        if(Productnotavailable)
        {
            Product product = modelMapper.map(productDTO, Product.class);
            product.setImage("default.png");
            product.setCategory(category);
            double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
    }
        else throw new ApiException("Product already exist!!");
    }


    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .toList();

        if(products.isEmpty())
            throw new ApiException("Resource not found !!");

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category =  categoryRepository.findById(categoryId)
                                    .orElseThrow(() ->new ResourceNotFoundException("Category","categoryId",categoryId));

        List<Product>products=productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO>productDTOS=products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();



        ProductResponse productResponse= new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword){
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        List<ProductDTO>productDTOS=products.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                .toList();

        ProductResponse productResponse= new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId,Product product)
    {
        Product existing_product=productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        existing_product.setProductId(product.getProductId());
        existing_product.setProductName(product.getProductName());
        existing_product.setPrice(product.getPrice());
        existing_product.setQuantity(product.getQuantity());
        existing_product.setDescription(product.getDescription());
        existing_product.setSpecialPrice(product.getSpecialPrice());

        Product savedProduct=productRepository.save(existing_product);

        /*new code*/
        List<Cart>carts=cartRepository.findCartsByProductId(productId);

        List<CartDTO> cartDTOS = carts.stream().map(cart->{
            CartDTO cartDTO=modelMapper.map(cart, CartDTO.class);

            List<ProductDTO> products = cart.getCartItems().stream()
                    .map(p->modelMapper.map(p.getProduct(),ProductDTO.class)).collect(Collectors.toList());

            cartDTO.setProducts(products);

            return cartDTO;


        }).collect(Collectors.toList());

        cartDTOS.forEach(cart -> cartService.updateProductInCarts(cart.getCartId(), productId));

        /*end of new code*/
        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId)
    {
        Product product=productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));


        //new code
        List<Cart>carts=cartRepository.findCartsByProductId(productId);
        carts.forEach(cart->cartService.deleteProductFromCart(cart.getCartId() ,productId));
        //end of new code

        productRepository.delete(product);

        return modelMapper.map(product,ProductDTO.class);

    }


}
