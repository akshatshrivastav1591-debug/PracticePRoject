package com.UpgradingSkillsDemo.Test.GlobalExceptionHandeller.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {
    private String ExceptionType;
    private String errorMessage;
}
