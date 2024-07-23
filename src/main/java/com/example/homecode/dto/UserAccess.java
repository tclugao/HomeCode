package com.example.homecode.dto;

import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class UserAccess implements Serializable {
    String userId;
    List<String> endpoint;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(List<String> endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String toString() {
        return "UserAccess{" +
                "userId='" + userId + '\'' +
                ", endpoint=" + endpoint +
                '}';
    }
}
