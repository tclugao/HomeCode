package com.example.homecode.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class Base64EncodeDecodeTests {
    @Test
    void encodeAndDecode() {

        String userId = "123";
        String accountName = "Gao";
        String role = "admin";

        String roleInfo = "userId:" + userId + "," + "accountName:"+accountName + ","+"role:"+role;

        String encodedRoleInfo = Base64.getEncoder().encodeToString(roleInfo.getBytes(StandardCharsets.UTF_8));
        System.out.println(encodedRoleInfo);

        byte[] decodedRoleInfoBytes = Base64.getDecoder().decode(encodedRoleInfo);
        String decodedRoleInfo = new String(decodedRoleInfoBytes);
        assert roleInfo.equals(decodedRoleInfo);

        String[] infos = decodedRoleInfo.split(",");
        assert infos.length == 3;

        Map<String, String> map = new HashMap<>();
        for (String info : infos) {
            String[] kv = info.split(":");
            map.put(kv[0], kv[1]);
        }
        assert userId.equals(map.get("userId"));
        assert accountName.equals(map.get("accountName"));
        assert role.equals(map.get("role"));

    }

}
