package com.example.homecode.interceptors;

import com.example.homecode.exceptions.HeaderNoRoleInfoException;
import com.example.homecode.exceptions.IncorrectRoleInfoException;
import com.example.homecode.exceptions.UserNotAdminException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class HeaderCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        String userRoleInfoBase64 = request.getHeader("userRoleInfo");
        if (userRoleInfoBase64 != null) {
            byte[] bytes = Base64.getDecoder().decode(userRoleInfoBase64);
            String decodedInfo = new String(bytes, StandardCharsets.UTF_8);
            Map<String,String> roleInfo = parseRoleInfoHeader(decodedInfo);

            String role = roleInfo.get("role");
            if (role == null) {
                throw new IncorrectRoleInfoException(HttpStatus.BAD_REQUEST, "Incorrect Role Info in Header");
            }

            if (request.getMethod().equals("POST") && request.getRequestURI().equals("/admin/addUser") && !role.equals("admin")) {
                throw  new UserNotAdminException(HttpStatus.FORBIDDEN, "User Not Admin");
            }
            request.setAttribute("roleInfoMap", roleInfo);
            return true;

        } else {
            throw new HeaderNoRoleInfoException(HttpStatus.BAD_REQUEST, "Miss Role Info in Header");
        }
    }

    private Map<String,String> parseRoleInfoHeader(String decodedRoleInfo) {
        String[] infos = decodedRoleInfo.split(",");
        Map<String, String> map = new HashMap<>();
        for (String info : infos) {
            String[] kv = info.split(":");
            map.put(kv[0], kv[1]);
        }
        return map;
    }
}
