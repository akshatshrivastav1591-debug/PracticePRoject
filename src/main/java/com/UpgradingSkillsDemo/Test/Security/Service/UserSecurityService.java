package com.UpgradingSkillsDemo.Test.Security.Service;


import com.UpgradingSkillsDemo.Test.ApiWrapperClass.ApiWrapperClass;
import com.UpgradingSkillsDemo.Test.Security.Dto.SecurityRegisterResponseDtoObject;
import com.UpgradingSkillsDemo.Test.Security.Dto.SecurityRequestDtoClass;
import com.UpgradingSkillsDemo.Test.Security.Mapper.SecurityMapper;
import com.UpgradingSkillsDemo.Test.Security.Pojo.UserSecurityPojoClass;
import com.UpgradingSkillsDemo.Test.Security.Repo.UserSecurityRepo;
import com.UpgradingSkillsDemo.Test.Security.UserPrinciple.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;
@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
     private UserSecurityRepo repo;
    @Autowired
    private SecurityMapper securityMapper;

    @Override
    public UserDetails loadUserByUsername(String email)  {
        UserSecurityPojoClass fetchedUserDetails=repo.findById(email).orElseThrow(() ->new UsernameNotFoundException("User is not found:"));
        return new UserPrinciple(fetchedUserDetails);
    }
    public ResponseEntity<ApiWrapperClass<SecurityRegisterResponseDtoObject>> saveNewUser(SecurityRequestDtoClass newUserInfo,String encodedPassword) {
        UserSecurityPojoClass newUser=securityMapper.registerRequestDtoToUserSecurityPojo(newUserInfo);
        newUser.setPassword(encodedPassword);
        repo.save(newUser);
        return  ResponseEntity.ok(new ApiWrapperClass<>("New user created",true,new SecurityRegisterResponseDtoObject("Registered",true)));
    }
    public String getRole(String userEmailID){
        return  repo.getUserRole(userEmailID).orElseThrow(()->new RuntimeException("User role not found in database:"));
    }
}
