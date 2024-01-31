package com.example.airdns.global.loadbalance;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoadBalanceController {

    @GetMapping("/health-check")
    public ResponseEntity<Void> readLoadBalance(){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
