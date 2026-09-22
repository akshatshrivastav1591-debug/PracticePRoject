package com.UpgradingSkillsDemo.Test.CRUD.Service;

import com.UpgradingSkillsDemo.Test.ApiWrapperClass.ApiWrapperClass;
import com.UpgradingSkillsDemo.Test.CRUD.Dto.ProductRequestDto;
import com.UpgradingSkillsDemo.Test.CRUD.Dto.ProductResponseDto;
import com.UpgradingSkillsDemo.Test.CRUD.Dto.UpdateProductRequestObjectDto;
import com.UpgradingSkillsDemo.Test.CRUD.Mapper.ProductMapper;
import com.UpgradingSkillsDemo.Test.CRUD.Pojo.ProductPojo;
import com.UpgradingSkillsDemo.Test.CRUD.Repo.ProductRepo;
import com.UpgradingSkillsDemo.Test.GlobalExceptionHandeller.CustomException.ProductClassExceptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductRepo productRepo;
    public ResponseEntity<ApiWrapperClass<String>> addNewProduct(ProductRequestDto newProduct) {
        ProductPojo newProductPojo=productMapper.productRequestDtoToProductPojo(newProduct);
        newProductPojo.setLaunchDate(LocalDateTime.now());
        newProductPojo.setProductSecretInfo("Kuch khas nahi");
        productRepo.save(newProductPojo);
        return ResponseEntity.ok(new ApiWrapperClass<>("Success",true,"New Product added:"));
    }

    public ResponseEntity<ApiWrapperClass<String>> updateProduct(UpdateProductRequestObjectDto updateProductDto) {
          ProductPojo oldProduct=productRepo.findById(updateProductDto.getProductId()).orElseThrow(ProductClassExceptions::notFound);
          ProductPojo updatedProduct=productMapper.updatedRequestProductDtoToProductPojo(updateProductDto);
          updatedProduct.setProductSecretInfo(oldProduct.getProductSecretInfo());
          productRepo.save(updatedProduct);
        return ResponseEntity.ok(new ApiWrapperClass<>("Success",true,"Product Updated:"));


    }

    public ResponseEntity<ApiWrapperClass<String>> deleteProduct(int deleteProductId) {
        productRepo.deleteById(deleteProductId);
        return ResponseEntity.ok(new ApiWrapperClass<>("Success",true,"Product Deleted:"));
    }

    public ResponseEntity<ApiWrapperClass<ArrayList<ProductResponseDto>>> getAllProduct() {
        ArrayList<ProductResponseDto> productList=new ArrayList<>();
        List <ProductPojo> fetchedProducts=productRepo.findAll();
        if(fetchedProducts.isEmpty()) return ResponseEntity.ok(new ApiWrapperClass<>("No Product is Available:",true,null));

        for (ProductPojo singleProduct:fetchedProducts){
            productList.add(productMapper.productPojoToProductResponseDto(singleProduct));
        }
        return ResponseEntity.ok(new ApiWrapperClass<>("Success",true,productList));

    }

    public ResponseEntity<ApiWrapperClass<ProductResponseDto>> getSingleProduct(int productID) {
        ProductPojo fetchedProductPojo=productRepo.findById(productID).orElseThrow(ProductClassExceptions::notFound);
        return ResponseEntity.ok(new ApiWrapperClass<>("Success",true,productMapper.productPojoToProductResponseDto(fetchedProductPojo)));
    }
}
