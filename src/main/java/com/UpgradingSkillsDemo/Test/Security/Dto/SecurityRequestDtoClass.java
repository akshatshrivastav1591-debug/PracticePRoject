package com.UpgradingSkillsDemo.Test.Security.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityRequestDtoClass {
    private String email;
    private String password;
    private String role;
}
