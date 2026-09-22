package com.UpgradingSkillsDemo.Test.CRUD.Pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document(collection = "TestinCollection")
@AllArgsConstructor
@NoArgsConstructor
public class ProductPojo {
   @Id
    private int  productId;
    private String productName;
    private double productPrice;
    private String productType;
    private LocalDateTime launchDate;
    private String productSecretInfo;
}
