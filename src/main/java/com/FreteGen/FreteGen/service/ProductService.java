package com.FreteGen.FreteGen.service;

import com.FreteGen.FreteGen.dto.ProductDTO;
import com.FreteGen.FreteGen.repository.ProductRepository;
import com.FreteGen.FreteGen.repository.UserRepository;
import com.FreteGen.FreteGen.user.Clients;
import com.FreteGen.FreteGen.user.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

   private final ProductRepository repository;
   private final UserRepository userRepository;


    public ProductService(ProductRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository =userRepository ;

    }

    public Products createdProduct(ProductDTO productDTO){
        Products products = new Products();
        products.setName(productDTO.getName());
        products.setDescription(productDTO.getDescription());
        products.setQuantity(productDTO.getQuantity());
        products.setHeightCm(productDTO.getHeightCm());
        products.setLengthCm(productDTO.getLengthCm());
        products.setWeightKg(productDTO.getWeightKg());
        products.setWidthCm(productDTO.getWidthCm());
        products.setDeclaredValue(productDTO.getDeclaredValue());
        products.setUserId(getUserid());



        repository.save(products);
        return products;
    }
    private String getUserid(){

            String username = SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getName();

            Clients client = (Clients) userRepository.findByLogin(username);

            return client.getId();
        }


    }



