package com.FreteGen.FreteGen.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO{
    @NotBlank
    private String name;
    private  String description;
    private BigDecimal weightKg;
    private  BigDecimal lengthCm;
    private   BigDecimal heightCm;
    private  BigDecimal widthCm;
    private  BigDecimal declaredValue;
    private   Integer quantity;
}
