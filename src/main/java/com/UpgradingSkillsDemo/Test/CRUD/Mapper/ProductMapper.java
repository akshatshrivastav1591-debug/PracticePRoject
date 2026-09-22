package com.UpgradingSkillsDemo.Test.CRUD.Mapper;

import com.UpgradingSkillsDemo.Test.CRUD.Dto.ProductRequestDto;
import com.UpgradingSkillsDemo.Test.CRUD.Dto.ProductResponseDto;
import com.UpgradingSkillsDemo.Test.CRUD.Dto.UpdateProductRequestObjectDto;
import com.UpgradingSkillsDemo.Test.CRUD.Pojo.ProductPojo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "launchDate",ignore = true)
    @Mapping(target = "productSecretInfo",ignore = true)
    ProductPojo productRequestDtoToProductPojo(ProductRequestDto requestDto);

    ProductResponseDto productPojoToProductResponseDto(ProductPojo productPojo);
    @Mapping(target = "productSecretInfo",ignore = true)
    ProductPojo updatedRequestProductDtoToProductPojo(UpdateProductRequestObjectDto updateProductRequestObjectDto);
}
