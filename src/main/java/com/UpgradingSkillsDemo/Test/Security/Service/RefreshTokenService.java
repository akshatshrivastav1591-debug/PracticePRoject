package com.UpgradingSkillsDemo.Test.Security.Service;

import com.UpgradingSkillsDemo.Test.GlobalExceptionHandeller.CustomException.RefreshTokenExceptions;
import com.UpgradingSkillsDemo.Test.Security.Pojo.RefreshTokenPojo;
import com.UpgradingSkillsDemo.Test.Security.Repo.RefreshTokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class RefreshTokenService {
    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    public Map<String,Object> validateRefreshToken(Integer refreshTokenId) {
        RefreshTokenPojo oldRefreshTokenRecord=refreshTokenRepo.findById(refreshTokenId).orElseThrow(()->new RefreshTokenExceptions("Refresh token Record not found in database",404));
                if(!LocalDateTime.now().isBefore(oldRefreshTokenRecord.getExpirationTime())){
                    throw new RefreshTokenExceptions("Refresh token is expired,Please do a login",401);
                }
           RefreshTokenPojo newRefreshTokenRecord=new RefreshTokenPojo();
           newRefreshTokenRecord.setExpirationTime(LocalDateTime.now().plusDays(7));
           newRefreshTokenRecord.setUserEmail(oldRefreshTokenRecord.getUserEmail());
           refreshTokenRepo.save(newRefreshTokenRecord);
           refreshTokenRepo.delete(oldRefreshTokenRecord);
           return Map.of("email",oldRefreshTokenRecord.getUserEmail(),"newRefreshTokenRecordId",newRefreshTokenRecord.getRefreshTokenId());
    }

    public int createRefreshTokenID(String userEmail){
        RefreshTokenPojo refreshTokenPojo=new RefreshTokenPojo();
        refreshTokenPojo.setUserEmail(userEmail);
        refreshTokenPojo.setExpirationTime(LocalDateTime.now().plusDays(7));
        refreshTokenRepo.save(refreshTokenPojo);
        return refreshTokenPojo.getRefreshTokenId();
    }
 public void deleteRefreshTokenID(Integer refreshTokenId){
      int rowDeleted=refreshTokenRepo.deleteRefreshTokenId(refreshTokenId);
      if(rowDeleted==0) throw new RuntimeException("Old Refresh Token not deleted");

 }
}
