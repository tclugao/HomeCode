package com.example.homecode;

import com.example.homecode.dao.FileSystemUserAccessRepo;
import com.example.homecode.dto.UserAccess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

@SpringBootApplication
public class HomecodeApplication {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        SpringApplication.run(HomecodeApplication.class, args);
    }

}
