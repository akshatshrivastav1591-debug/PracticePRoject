package com.UpgradingSkillsDemo.Test.ApiWrapperClass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiWrapperClass<T> {
    private String message;
    private  boolean isSuccess;
    private  T data;
}
