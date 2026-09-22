package com.UpgradingSkillsDemo.Test.Security.Mapper;

import com.UpgradingSkillsDemo.Test.Security.Dto.SecurityRequestDtoClass;
import com.UpgradingSkillsDemo.Test.Security.Pojo.UserSecurityPojoClass;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel ="spring")
public interface SecurityMapper {
    @Mapping(target = "password",ignore = true)
    UserSecurityPojoClass registerRequestDtoToUserSecurityPojo(SecurityRequestDtoClass userDto);
}
