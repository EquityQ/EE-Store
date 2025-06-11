package org.example.javawebv2.com.v2.Controller;

import com.google.gson.Gson;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/system")
public class SystemController {

    @RequestMapping("/ip")
    public String ip(HttpServletRequest req) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        map.put("Code", 200);
        map.put("ip", req.getRemoteAddr());
        return gson.toJson(map);
    }

    @RequestMapping("/img/base64")
    public String imgBase64(@RequestParam("file") MultipartFile file) throws IOException {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        if (file.isEmpty()) {
            map.put("Code", 500);
            map.put("Response", "文件为空");
            return gson.toJson(map);
        }

        // 读取文件内容
        byte[] bytes = file.getBytes();

        // 将字节数组转换为 Base64 编码的字符串
        String base64String = Base64.getEncoder().encodeToString(bytes);
        base64String = "data:image/jepg;base64," + base64String;

        map.put("Code", 200);
        map.put("Response", "图片解析成功");
        map.put("base64", base64String);
        return gson.toJson(map);
    }
}