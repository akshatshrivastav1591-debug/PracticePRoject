package com.UpgradingSkillsDemo.Test.Security.Controller;


import com.UpgradingSkillsDemo.Test.ApiWrapperClass.ApiWrapperClass;
import com.UpgradingSkillsDemo.Test.GlobalExceptionHandeller.CustomException.RefreshTokenExceptions;
import com.UpgradingSkillsDemo.Test.Security.Dto.SecurityRegisterResponseDtoObject;
import com.UpgradingSkillsDemo.Test.Security.Dto.SecurityRequestDtoClass;
import com.UpgradingSkillsDemo.Test.Security.Dto.SecurityLoginResponseDtoClass;

import com.UpgradingSkillsDemo.Test.Security.Jwt.JwtFilterServiceClass;

import com.UpgradingSkillsDemo.Test.Security.Service.RefreshTokenService;
import com.UpgradingSkillsDemo.Test.Security.Service.UserSecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SecurityController {
    @Autowired
    private  AuthenticationManager authenticationManager;
    @Autowired
    private  JwtFilterServiceClass jwtFilterServiceClass;
    @Autowired
    private UserSecurityService userSecurityService;
    @Autowired
    private BCryptPasswordEncoder encoder;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login") //user Login Method:
    public ResponseEntity<ApiWrapperClass<SecurityLoginResponseDtoClass>> UserLogin(@RequestBody SecurityRequestDtoClass user, HttpServletResponse response) {


        Authentication authorized = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        if (authorized.isAuthenticated()) {
            String accessToken = jwtFilterServiceClass.getAccessToken(user.getEmail(), user.getRole());
            String refreshToken= jwtFilterServiceClass.getRefreshToken(refreshTokenService.createRefreshTokenID(user.getEmail()));

            response.setHeader("Set-Cookie",
                    "jwt=" + refreshToken +
                            "; Path=/" +
                            "; Max-Age=604800" +
                            "; HttpOnly" +
                            "; SameSite=Lax" +
                            "; Secure"
                    // ✅ No Secure flag — works on HTTP localhost
            );
            return ResponseEntity.ok(new ApiWrapperClass<>("All is good:", true, new SecurityLoginResponseDtoClass("Logged In:", accessToken)));
        }
        throw new BadCredentialsException("Something went wrong with authenticate method");
    }

   @PostMapping("/register/user")
      ResponseEntity<ApiWrapperClass<SecurityRegisterResponseDtoObject>>  registerUser(@RequestBody SecurityRequestDtoClass newUserInfo){
        String encodedPassword=encoder.encode(newUserInfo.getPassword());
        return  userSecurityService.saveNewUser(newUserInfo,encodedPassword);
      }
   @PostMapping("/renewRefeshToken")
   ResponseEntity<ApiWrapperClass<SecurityLoginResponseDtoClass>> renewRefreshToken(HttpServletResponse response, HttpServletRequest request){
        String extractedRefreshToken=jwtFilterServiceClass.extractRefreshTokenFromCookies(request);
        if(extractedRefreshToken==null) throw new RefreshTokenExceptions("Refresh Token not found:",404);
        Map<String,Object> renewalRefreshTokenObject=refreshTokenService.validateRefreshToken(jwtFilterServiceClass.extractRefreshTokenId(extractedRefreshToken));
       response.setHeader("Set-Cookie",
               "jwt=" + extractedRefreshToken +
                       "; Path=/" +
                       "; Max-Age=0" +
                       "; HttpOnly" +
                       "; SameSite=Lax" +
                       "; Secure"
       );
       String renewedRefreshToken= jwtFilterServiceClass.getRefreshToken((Integer) renewalRefreshTokenObject.get("newRefreshTokenRecordId"));
       String renewedAccessToken= jwtFilterServiceClass.getAccessToken((String) renewalRefreshTokenObject.get("email"),userSecurityService.getRole((String) renewalRefreshTokenObject.get("email")));
       response.setHeader("Set-Cookie",
               "jwt=" + renewedRefreshToken +
                       "; Path=/" +
                       "; Max-Age=604800" +
                       "; HttpOnly" +
                       "; SameSite=Lax" +
                       "; Secure"

       );
        return  ResponseEntity.ok(new ApiWrapperClass<>("Operation Success",true,new SecurityLoginResponseDtoClass("Refresh Token is renewed",renewedAccessToken)));
   }

   @DeleteMapping("/userLogout")
    public ResponseEntity<ApiWrapperClass<String>> logout(HttpServletRequest request,HttpServletResponse response){
       String extractedRefreshToken=jwtFilterServiceClass.extractRefreshTokenFromCookies(request);
       if(extractedRefreshToken==null){ throw new RuntimeException("Jwt Token not found:");}
            refreshTokenService.deleteRefreshTokenID(jwtFilterServiceClass.extractRefreshTokenId(extractedRefreshToken));
            response.setHeader("Set-Cookie",
                    "jwt=" + extractedRefreshToken +
                            "; Path=/" +
                            "; Max-Age=0" +
                            "; HttpOnly" +
                            "; SameSite=Lax" +
                            "; Secure"
            );

        return  ResponseEntity.ok(new ApiWrapperClass<>("Operation success",true,"Successfully logout:"));
   }

}
