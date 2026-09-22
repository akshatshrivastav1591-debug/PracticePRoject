package com.UpgradingSkillsDemo.Test.DemoController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
    @GetMapping("/getTestinDetails")
    public ResponseEntity<String> getTestingDetails(){
       return ResponseEntity.ok("Yup security is working fine;");
    }
}
