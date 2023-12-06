package com.lunifer.jo.fpshoppingcart.service.impl;

import com.lunifer.jo.fpshoppingcart.dto.CategoryDTO;
import com.lunifer.jo.fpshoppingcart.dto.ProductDTO;
import com.lunifer.jo.fpshoppingcart.entity.Product;
import com.lunifer.jo.fpshoppingcart.entity.Review;
import com.lunifer.jo.fpshoppingcart.exception.ResourceNotFoundException;
import com.lunifer.jo.fpshoppingcart.mapper.CategoryMapper;
import com.lunifer.jo.fpshoppingcart.mapper.ProductMapper;
import com.lunifer.jo.fpshoppingcart.payload.ProductResponse;
import com.lunifer.jo.fpshoppingcart.repository.CategoryRepository;
import com.lunifer.jo.fpshoppingcart.repository.ProductRepository;
import com.lunifer.jo.fpshoppingcart.repository.ReviewRepository;
import com.lunifer.jo.fpshoppingcart.service.CategoryService;
import com.lunifer.jo.fpshoppingcart.service.ProductService;
import com.lunifer.jo.fpshoppingcart.service.ReviewService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;
    private final CategoryMapper categoryMapper;

    private final ReviewRepository reviewRepository;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper,
                              CategoryMapper categoryMapper, CategoryService categoryService, ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
        this.categoryService = categoryService;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ProductResponse getAllProducts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // Create a Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // Retrieve a page of posts
        Page<Product> productList = productRepository.findAll(pageable);

        // Get content for page object
        List<Product> listOfProduct = productList.getContent();

        List<ProductDTO> content = listOfProduct.stream()
                .map(productMapper::productEntityToProductDTO).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(content);
        productResponse.setPageNo(productList.getNumber());
        productResponse.setPageSize(productList.getSize());
        productResponse.setTotalElements(productList.getTotalElements());
        productResponse.setTotalPages(productList.getTotalPages());
        productResponse.setLast(productList.isLast());
        return productResponse;
    }

    @Override
    @Transactional
    public ProductDTO getProductById(long productId) {
        return productRepository.findById(productId)
                .map(this::mapProductToDTOWithCategory)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
    }

    @Override
    @Transactional
    public ProductDTO getProductByName(String productName) {
        return productRepository.findByProductName(productName)
                .map(this::mapProductToDTOWithCategory)
                .orElseThrow(() -> new NoSuchElementException("Product with name " + productName + " not found"));

    }

    @Override
    @Transactional
    public ProductDTO updateProduct(ProductDTO productDTO, long productId) {
        // 1. Check whether the product with the given ID exists in DB or not
        //Throw exception
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        // 2. Map the updated fields from productDTO to the existing productEntity
        existingProduct.setProductName(productDTO.getProductName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setStock(productDTO.getStock());
        existingProduct.setActive(productDTO.isActive());
        existingProduct.setCategory(categoryMapper.categoryDTOToCategoryEntity(categoryService.getCategoryById(productDTO.getCategoryId())));

        // 3. Save the updated productEntity back to the database
        Product updatedProductEntity = productRepository.save(existingProduct);

        // 4. Map the updated productEntity to a ProductDTO and return it
        return mapProductToDTOWithCategory(updatedProductEntity);

    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO) {
        // Perform validation or transformation logic before saving to the database
        validateProductDTO(productDTO); // Your custom validation method

        // Map the DTO to an entity and save it to the database
        Product productEntityToSave = productMapper.productDTOToProductEntity(productDTO);
        productEntityToSave.setCategory(categoryMapper.categoryDTOToCategoryEntity(categoryService.getCategoryById(productDTO.getCategoryId())));

        Product savedProductEntity = productRepository.save(productEntityToSave);

        // Map the saved entity back to a DTO and return it
        return mapProductToDTOWithCategory(savedProductEntity);

    }

    @Override
    public void deleteProduct(long productId) {
        List<Review> reviewList = reviewRepository.findReviewsByProductId(productId);
        if (!reviewList.isEmpty()) {
            reviewList.forEach(review -> reviewRepository.delete(review));
        }



        // Delete the product
        productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        productRepository.deleteById(productId);
    }

    private void validateProductDTO(ProductDTO productDTO) {

        // Check if product name is not null and not empty
        if (productDTO.getProductName() == null || productDTO.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }

        // Check if price is not null and greater than zero
        if (productDTO.getPrice() == null || productDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Product price must be greater than zero");
        }

        // Check if stock is not negative
        if (productDTO.getStock() < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }

        // Check if the category is provided (assuming you have a CategoryDTO in ProductDTO)
        if (productDTO.getCategoryId() == 0) {
            throw new IllegalArgumentException("Product category is required");
        }
    }

    @Override
    @Transactional
    public String DisableEnableProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();

            // Check if the category of the product is not active before allowing the activation of the product
            if (product.getCategory() != null && !product.getCategory().isActive()) {
                throw new IllegalStateException("Cannot activate the product '" + product.getProductName() +
                        "' with ID: " + product.getProductId() +
                        " because its category '" + product.getCategory().getCategoryName() +
                        "' with ID: " + product.getCategory().getCategoryId() + " is not active");
            }

            // Toggle the 'isActive' attribute (change it to the opposite value)
            product.setActive(!product.isActive());

            // Since we're using @Transactional, changes will be automatically
            // saved to the database when the transaction is committed

            // Return a message indicating whether the product was successfully disabled or enabled
            return "Product: " + product.getProductName() + " with ID: " + product.getProductId() +
                    " has been successfully " + (product.isActive() ? "enabled" : "disabled");
        } else {
            throw new EntityNotFoundException("Cannot find product with ID " + productId);
        }
    }


    @Override
    @Transactional
    public List<ProductDTO> findAllProductsByCategoryId(long category) {
        return productRepository.findAllByCategoryCategoryId(category)
                .stream()
                .map(productMapper::productEntityToProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteAllByCategoryId(long category) {
        productRepository.deleteAllByCategoryCategoryId(category);
    }

    private ProductDTO mapProductToDTOWithCategory(Product product) {
        ProductDTO productDTO = productMapper.productEntityToProductDTO(product);
        CategoryDTO categoryDTO = categoryMapper.categoryEntityToCategoryDTO(product.getCategory());
        productDTO.setCategoryId(categoryDTO.getCategoryId());
        return productDTO;
    }

    @Override
    @Transactional
    public List<ProductDTO> getProductsByKeyword(String keyword) {
        List<Product> products = productRepository.findByKeyword(keyword);
        return products.stream()
                .map(this::mapProductToDTOWithCategory)
                .collect(Collectors.toList());
    }

}