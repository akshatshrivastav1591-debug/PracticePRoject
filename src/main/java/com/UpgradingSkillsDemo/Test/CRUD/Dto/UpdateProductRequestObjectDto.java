package com.UpgradingSkillsDemo.Test.CRUD.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequestObjectDto {
    private int  productId;
    private String productName;
    private double productPrice;
    private String productType;
    private LocalDateTime launchDate;

}
