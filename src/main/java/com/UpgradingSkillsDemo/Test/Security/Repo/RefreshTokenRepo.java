package com.UpgradingSkillsDemo.Test.Security.Repo;

import com.UpgradingSkillsDemo.Test.Security.Pojo.RefreshTokenPojo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshTokenRepo extends JpaRepository<RefreshTokenPojo,Integer> {
    @Modifying
    @Transactional
    @Query("Delete from RefreshTokenPojo r where r.refreshTokenId=:refreshTokenId")
    int deleteRefreshTokenId(int refreshTokenId);
}
