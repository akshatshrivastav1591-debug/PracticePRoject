package com.UpgradingSkillsDemo.Test.CRUD.Controller;

import com.UpgradingSkillsDemo.Test.ApiWrapperClass.ApiWrapperClass;

import com.UpgradingSkillsDemo.Test.CRUD.Dto.ProductRequestDto;
import com.UpgradingSkillsDemo.Test.CRUD.Dto.ProductResponseDto;
import com.UpgradingSkillsDemo.Test.CRUD.Dto.UpdateProductRequestObjectDto;
import com.UpgradingSkillsDemo.Test.CRUD.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

@RestController
public class Controller {
    @Autowired
    ProductService productService;
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addProduct")
    public ResponseEntity<ApiWrapperClass<String>> addNewProduct(@RequestBody ProductRequestDto newProduct){
        return productService.addNewProduct(newProduct);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateProduct")
    public  ResponseEntity<ApiWrapperClass<String>> updateProduct(@RequestBody UpdateProductRequestObjectDto updateProductDto){
        return productService.updateProduct(updateProductDto);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteProduct")
    public  ResponseEntity<ApiWrapperClass<String>>  deleteProduct(@RequestBody int  deleteProductId){
        return productService.deleteProduct(deleteProductId);
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/getAllProducts")
    public  ResponseEntity<ApiWrapperClass<ArrayList<ProductResponseDto>>>  getAllProducts(){
        return productService.getAllProduct();
    }
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/getSingleProduct/{productID}")
    public  ResponseEntity<ApiWrapperClass<ProductResponseDto>>  getSingleProduct(@PathVariable int productID){
        return productService.getSingleProduct(productID);
    }
 }
