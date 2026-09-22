package com.UpgradingSkillsDemo.Test.GlobalExceptionHandeller;

import com.UpgradingSkillsDemo.Test.ApiWrapperClass.ApiWrapperClass;
import com.UpgradingSkillsDemo.Test.GlobalExceptionHandeller.CustomException.AccessTokenExceptions;
import com.UpgradingSkillsDemo.Test.GlobalExceptionHandeller.CustomException.ProductClassExceptions;
import com.UpgradingSkillsDemo.Test.GlobalExceptionHandeller.CustomException.RefreshTokenExceptions;
import com.UpgradingSkillsDemo.Test.GlobalExceptionHandeller.Dto.ExceptionDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionalHandeller {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiWrapperClass<ExceptionDto>> genericExceptionMethod(Exception e){
        System.out.println("Error:"+e.getLocalizedMessage());
        return  ResponseEntity.status(500).body(new ApiWrapperClass<>("Something went wrong with server",false,new ExceptionDto("genericException","Error:Something went wrong with server:")));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiWrapperClass<ExceptionDto>> badCredentialExceptionMethod(BadCredentialsException e){
        System.out.println("Error:"+e.getLocalizedMessage());
        return  ResponseEntity.status(401).body(new ApiWrapperClass<>("Details not fetched",false,new ExceptionDto("BadCredentialsException","Please Enter Correct Credentials/Info")));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiWrapperClass<ExceptionDto>> badCredentialExceptionMethod(UsernameNotFoundException e){
        System.out.println("Error:"+e.getLocalizedMessage());
        return  ResponseEntity.status(404).body(new ApiWrapperClass<>("Details not fetched",false,new ExceptionDto("Username Not found Exception","User is not available")));
    }

    @ExceptionHandler(RefreshTokenExceptions.class)
    public ResponseEntity<ApiWrapperClass<ExceptionDto>> refreshTokenExceptionMethod(RefreshTokenExceptions e){
        System.out.println("Error:"+e.getLocalizedMessage());
        return  ResponseEntity.status(e.getStatusCode()).body(new ApiWrapperClass<>("refresh Token renewal denied",false,new ExceptionDto("RefreshTokenException",e.getLocalizedMessage())));
    }

    @ExceptionHandler(AccessTokenExceptions.class)
    public ResponseEntity<ApiWrapperClass<ExceptionDto>> accessTokenExceptionMethod(AccessTokenExceptions e){
        System.out.println("Error:"+e.getLocalizedMessage());
        return  ResponseEntity.status(e.getStatusCode()).body(new ApiWrapperClass<>("Request Denied:",false,new ExceptionDto("AccessTokenException",e.getLocalizedMessage())));
    }


    @ExceptionHandler(ProductClassExceptions.class)
    public ResponseEntity<ApiWrapperClass<ExceptionDto>> productClassExceptionMethod(ProductClassExceptions e){
        log.error("Error at Product Management:{}", e.getLocalizedMessage());
        return  ResponseEntity.status(e.getStatusCode()).body(new ApiWrapperClass<>("Request Denied:",false,new ExceptionDto("Product Class Exception",e.getLocalizedMessage())));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiWrapperClass<ExceptionDto>> authenticationException(AuthenticationException e){
        log.error("Authentication Error:{}", e.getLocalizedMessage());
        return  ResponseEntity.status(401).body(new ApiWrapperClass<>("Not Authorized:",false,new ExceptionDto("Authentication Exception",e.getLocalizedMessage())));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiWrapperClass<ExceptionDto>> accessDeniedException(AccessDeniedException e){
        log.error("AccessDenied Error:{}", e.getLocalizedMessage());
        return  ResponseEntity.status(401).body(new ApiWrapperClass<>("Access Denied:",false,new ExceptionDto("Access Denied  Exception",e.getLocalizedMessage())));
    }





}
