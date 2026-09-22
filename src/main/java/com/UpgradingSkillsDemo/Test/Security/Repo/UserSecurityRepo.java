package com.UpgradingSkillsDemo.Test.Security.Repo;

import com.UpgradingSkillsDemo.Test.Security.Pojo.UserSecurityPojoClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserSecurityRepo extends JpaRepository<UserSecurityPojoClass,String> {
    @Query("select u.role from UserSecurityPojoClass u where u.email=:userEmailId")
    Optional<String> getUserRole(String userEmailId);

}
