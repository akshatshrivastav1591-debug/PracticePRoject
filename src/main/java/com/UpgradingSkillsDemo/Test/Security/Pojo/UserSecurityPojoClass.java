package com.UpgradingSkillsDemo.Test.Security.Pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSecurityPojoClass {
    @Id
    private String email;
    private String password;
    private String role;

}
