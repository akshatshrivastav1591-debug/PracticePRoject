package com.UpgradingSkillsDemo.Test.CRUD.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    @NotNull
    private String productName;
    @NotNull
    private double productPrice;
    @NotNull
    private  int productId;

    private String productType;
}
