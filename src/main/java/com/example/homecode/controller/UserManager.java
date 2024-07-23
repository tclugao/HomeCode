package com.example.homecode.controller;

import com.example.homecode.dao.FileSystemUserAccessRepo;
import com.example.homecode.dto.UserAccess;
import com.example.homecode.exceptions.UserNotAdminException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserManager {
    @Autowired
    private FileSystemUserAccessRepo fileSystemUserAccessRepo;

    @PostMapping("/admin/addUser")

    public ResponseEntity<String> addUser(@RequestBody UserAccess userAccess) {
        fileSystemUserAccessRepo.save(userAccess);
        return ResponseEntity.status(HttpStatus.CREATED).body(userAccess.getUserId() + " is added");
    }

    @GetMapping("/user/{resourceId}")
    public  ResponseEntity<Map<String, String>> checkAccess(@PathVariable("resourceId") String resourceId, HttpServletRequest request) {
        Map<String,String> roleInfo = (Map<String, String>) request.getAttribute("roleInfoMap");
        String userId = roleInfo.get("userId");

        Map<String,String> result = new HashMap<>();
        Optional<UserAccess> raw =  fileSystemUserAccessRepo.findById(userId);
        if (!raw.isEmpty()) {
            UserAccess userAccess = raw.get();
            List<String> endpoints = userAccess.getEndpoint();
             if (endpoints.contains(resourceId)) {
                 result.put("isAccess", "true");
             } else {
                 result.put("isAccess", "false");
             }
        } else {
            result.put("isAccess", "false");
        }
        result.put("resource", resourceId);
        result.put("userId", userId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}
