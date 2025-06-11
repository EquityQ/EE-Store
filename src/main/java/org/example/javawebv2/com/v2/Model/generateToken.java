package org.example.javawebv2.com.v2.Model;

import java.util.UUID;

public class generateToken {

    /**
     * 生成一个随机的 32 位 token
     * @return 32 位的 token 字符串
     */
    public static String Token() {
        // 生成一个 UUID
        UUID uuid = UUID.randomUUID();
        // 将 UUID 转换为字符串并去除所有连字符
        String token = uuid.toString().replace("-", "");
        // 截取前 32 位
        return token.substring(0, 32);
    }
}
