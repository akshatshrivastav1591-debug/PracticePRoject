package com.UpgradingSkillsDemo.Test.GlobalExceptionHandeller.CustomException;

import lombok.Getter;

@Getter
public class RefreshTokenExceptions extends RuntimeException {
    private final int  statusCode;
    public RefreshTokenExceptions(String message,int receivedStatusCode) {
        super(message);
        statusCode=receivedStatusCode;


    }
}
