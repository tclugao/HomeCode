package com.example.homecode.controller;

import com.example.homecode.dto.UserAccess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserManagerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddUserWithoutRequiredHeader() throws Exception {
        String userId = "12345";
        List<String> rs = Arrays.asList("r1", "r2", "r3");
        UserAccess userAccess = new UserAccess();
        userAccess.setUserId(userId);
        userAccess.setEndpoint(rs);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userAccess);

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Miss Role Info In Header"));
    }

    @Test
    public void  testAddUserNotAdmin() throws  Exception {
        String userId = "12345";
        List<String> rs = Arrays.asList("r1", "r2", "r3");
        UserAccess userAccess = new UserAccess();
        userAccess.setUserId(userId);
        userAccess.setEndpoint(rs);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userAccess);

        String roleInfo = "userId:" + userId + "," + "accountName:"+"Lugao" + ","+"role:"+"user";

        String encodedRoleInfo = Base64.getEncoder().encodeToString(roleInfo.getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("userRoleInfo", encodedRoleInfo)
                       )
                .andExpect(status().isForbidden())
                .andExpect(content().string("User Not Admin"));
    }

    @Test
    public void  testAddUserWithIncorrectRoleInfo() throws  Exception {
        String userId = "12345";
        List<String> rs = Arrays.asList("r1", "r2", "r3");
        UserAccess userAccess = new UserAccess();
        userAccess.setUserId(userId);
        userAccess.setEndpoint(rs);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userAccess);

        String roleInfo = "userId:" + userId + "," + "accountName:"+"Lugao";

        String encodedRoleInfo = Base64.getEncoder().encodeToString(roleInfo.getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("userRoleInfo", encodedRoleInfo)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Incorrect Role Info In Header"));
    }

    @Test
    public void  testAddUser() throws  Exception {
        String userId = "12345";
        List<String> rs = Arrays.asList("r1", "r2", "r3");
        UserAccess userAccess = new UserAccess();
        userAccess.setUserId(userId);
        userAccess.setEndpoint(rs);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(userAccess);

        String roleInfo = "userId:" + "455" + "," + "accountName:"+"Lugao" + ","+"role:"+"admin";

        String encodedRoleInfo = Base64.getEncoder().encodeToString(roleInfo.getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/addUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .header("userRoleInfo", encodedRoleInfo))
                .andExpect(status().isCreated())
                .andExpect(content().string(userId+ " is added"));
    }

    @Test
    public void testUserAccessWithoutHeaders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/resourceA"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUserAccessWithHeader() throws Exception {

        testAddUser();

        String roleInfo = "userId:" + "12345" + "," + "accountName:"+"Lugao" + ","+"role:"+"user";

        String encodedRoleInfo = Base64.getEncoder().encodeToString(roleInfo.getBytes(StandardCharsets.UTF_8));

        Map<String,String> result = new HashMap<>();
        result.put("isAccess", "true");
        result.put("resource", "r1");
        result.put("userId", "12345");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/r1")
                        .header("userRoleInfo", encodedRoleInfo))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

    @Test
    public void testUserAccessWithHeader2() throws Exception {

        testAddUser();

        String roleInfo = "userId:" + "12345" + "," + "accountName:"+"Lugao" + ","+"role:"+"user";

        String encodedRoleInfo = Base64.getEncoder().encodeToString(roleInfo.getBytes(StandardCharsets.UTF_8));

        Map<String,String> result = new HashMap<>();
        result.put("isAccess", "false");
        result.put("resource", "r4");
        result.put("userId", "12345");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/r4")
                        .header("userRoleInfo", encodedRoleInfo))
                .andExpect(status().isOk())
                .andExpect(content().json(json));
    }

}
