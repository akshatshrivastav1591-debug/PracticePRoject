package com.UpgradingSkillsDemo.Test.GlobalExceptionHandeller.CustomException;

import lombok.Getter;

@Getter
public class AccessTokenExceptions extends  RuntimeException {
    private final int statusCode;

    public AccessTokenExceptions(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    //Helper Function for Expired Access Token exception
    public static  AccessTokenExceptions expired(){
        return  new AccessTokenExceptions("Access Token is expired",401);
    }
    //Helper Function for Invalid Access Token exception
    public static AccessTokenExceptions invalid() {
        return  new AccessTokenExceptions("Access Token is Invalid",401);
    }
}
