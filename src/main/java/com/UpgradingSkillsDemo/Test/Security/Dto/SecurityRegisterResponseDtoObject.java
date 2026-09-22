package com.UpgradingSkillsDemo.Test.Security.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityRegisterResponseDtoObject {
    private String message;
    private boolean isRegistered;
}
