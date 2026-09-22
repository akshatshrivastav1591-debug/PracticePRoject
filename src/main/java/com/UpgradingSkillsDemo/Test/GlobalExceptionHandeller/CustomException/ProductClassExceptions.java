package com.UpgradingSkillsDemo.Test.GlobalExceptionHandeller.CustomException;

import lombok.Getter;

@Getter
public class ProductClassExceptions extends RuntimeException {
    private final int statusCode;
    public ProductClassExceptions(String message,int statusCode) {

        super(message);
        this.statusCode=statusCode;
    }

    public static ProductClassExceptions notFound(){
        return new ProductClassExceptions("Product Not Found:",404);
    }


}
