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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;


@Service
public class ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    RedisTemplate<String,Object> redisTemplate;
    private  static  final String generalKey="product";
    public ResponseEntity<ApiWrapperClass<String>> addNewProduct(ProductRequestDto newProduct) {
        ProductPojo newProductPojo=productMapper.productRequestDtoToProductPojo(newProduct);
        newProductPojo.setLaunchDate(LocalDateTime.now());
        newProductPojo.setProductSecretInfo("Kuch khas nahi");
        productRepo.save(newProductPojo);
        return ResponseEntity.ok(new ApiWrapperClass<>("Success",true,"New Product added:"));
    }

    public ResponseEntity<ApiWrapperClass<String>> updateProduct(UpdateProductRequestObjectDto updateProductDto) {
          String objectKey=generalKey+updateProductDto.getProductId();
          ProductPojo oldProduct=productRepo.findById(updateProductDto.getProductId()).orElseThrow(ProductClassExceptions::notFound);
          ProductPojo updatedProduct=productMapper.updatedRequestProductDtoToProductPojo(updateProductDto);
          updatedProduct.setProductSecretInfo(oldProduct.getProductSecretInfo());
          productRepo.save(updatedProduct);
          redisTemplate.opsForHash().put(objectKey,String.valueOf(updateProductDto.getProductId()),updatedProduct);
        return ResponseEntity.ok(new ApiWrapperClass<>("Success",true,"Product Updated:"));


    }

    public ResponseEntity<ApiWrapperClass<String>> deleteProduct(int deleteProductId) {
        String objectKey=generalKey+deleteProductId;
        productRepo.deleteById(deleteProductId);
        redisTemplate.opsForHash().delete(objectKey,String.valueOf(deleteProductId));
        return ResponseEntity.ok(new ApiWrapperClass<>("Success",true,"Product Deleted:"));
    }
    @Cacheable(key = "'AllProducts'",value = "products",unless = "#result == null")
    public ArrayList<ProductResponseDto> getAllProduct() {
        ArrayList<ProductResponseDto> productList=new ArrayList<>();
        ArrayList <ProductPojo> fetchedProducts= (ArrayList<ProductPojo>) productRepo.findAll();
        if(fetchedProducts.isEmpty()) return null;

        for (ProductPojo singleProduct:fetchedProducts){
            productList.add(productMapper.productPojoToProductResponseDto(singleProduct));
        }
        return productList;
    }

    public ResponseEntity<ApiWrapperClass<ProductResponseDto>> getSingleProduct(int productID) {
    String objectKey=generalKey+productID;
        ProductPojo cachedProductPojo = (ProductPojo) redisTemplate.opsForHash().get(objectKey, String.valueOf(productID));
        if (cachedProductPojo != null)
            return ResponseEntity.ok(new ApiWrapperClass<>("Success", true, productMapper.productPojoToProductResponseDto(cachedProductPojo)));
        else {
            ProductPojo fetchedProductPojo = productRepo.findById(productID).orElseThrow(ProductClassExceptions::notFound);

            redisTemplate.opsForHash().put(objectKey, String.valueOf(fetchedProductPojo.getProductId()), fetchedProductPojo);
            redisTemplate.expire(objectKey, Duration.ofMinutes(10));
            return ResponseEntity.ok(new ApiWrapperClass<>("Success", true, productMapper.productPojoToProductResponseDto(fetchedProductPojo)));
        }
    }

}
