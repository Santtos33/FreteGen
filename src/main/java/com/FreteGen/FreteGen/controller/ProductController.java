package com.FreteGen.FreteGen.controller;

import com.FreteGen.FreteGen.dto.ProductDTO;
import com.FreteGen.FreteGen.repository.ProductRepository;
import com.FreteGen.FreteGen.repository.UserRepository;
import com.FreteGen.FreteGen.security.SecurityConfig;
import com.FreteGen.FreteGen.service.ProductService;
import com.FreteGen.FreteGen.user.Clients;
import com.FreteGen.FreteGen.user.Products;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;
    private ProductRepository productRepository;
    private UserRepository userRepository;


    public ProductController(ProductService productService, ProductRepository productRepository, UserRepository userRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;

    }

    @PostMapping("/create")
    public ResponseEntity<Products> createProduct(@RequestBody ProductDTO productDTO) {
        Products product = productService.createdProduct(productDTO);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/getProduct/{id}")
    public ResponseEntity getProducts(@PathVariable("id") UUID id) {
        var products = productRepository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @DeleteMapping("/delete")
    private ResponseEntity deleteProduct(@PathVariable("id") UUID id) {
        var product = productRepository.findById(id);
        productRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(product.get().getId());
    }

}
