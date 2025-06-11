package org.example.javawebv2.com.v2.Controller;

import com.google.gson.Gson;
import org.example.javawebv2.com.v2.Model.MyShopElement;
import org.example.javawebv2.com.v2.Model.element;
import org.example.javawebv2.com.v2.Service.PublicService;
import org.example.javawebv2.com.v2.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/element")
public class ElementController {
    private final PublicService elementService;
    private final TokenService tokenService;

    @Autowired
    public ElementController(PublicService elementService, TokenService tokenService) {
        this.elementService = elementService;
        this.tokenService = tokenService;
    }

    @RequestMapping("/info")
    public String elementInfo() {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        map.put("Code", 200);
        List<element> elements = elementService.getAllElements();
        map.put("elements", elements);
        return gson.toJson(map);
    }

    @PostMapping("/delete")
    public String deleteElement(@RequestHeader String token, @RequestParam String name) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        if (tokenService.validateToken(token)) {
            String permission = tokenService.getPermission(token);
            if (permission.equals("admin")) {
                if (elementService.deleteElement(name)) {
                    map.put("Code", 200);
                    map.put("Response", "删除成功");
                } else {
                    map.put("Code", 500);
                    map.put("Response", "服务器错误");
                }
            } else {
                map.put("Code", 401);
                map.put("Response", "权限不足");
            }
        } else {
            map.put("Code", 401);
            map.put("Response", "Token验证失败");
        }
        return gson.toJson(map);
    }

    @PostMapping("/change")
    public String changeElement(@RequestHeader String token, @RequestBody MyShopElement element) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        if (tokenService.validateToken(token)) {
            String permission = tokenService.getPermission(token);
            if (permission.equals("admin")) {
                if (elementService.changeElement(element)) {
                    map.put("Code", 200);
                    map.put("Response", "修改成功");
                } else {
                    map.put("Code", 500);
                    map.put("Response", "服务器错误");
                }
            } else {
                map.put("Code", 401);
                map.put("Response", "权限不足");
            }
        } else {
            map.put("Code", 401);
            map.put("Response", "Token验证失败");
        }
        return gson.toJson(map);
    }

    @PostMapping("/insert")
    public String insertElement(@RequestHeader String token, @RequestBody MyShopElement element) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        if (tokenService.validateToken(token)) {
            String permission = tokenService.getPermission(token);
            if (permission.equals("admin")) {
                if (elementService.insertElement(element)) {
                    map.put("Code", 200);
                    map.put("Response", "添加成功");
                } else {
                    map.put("Code", 500);
                    map.put("Response", "服务器错误");
                }
            } else {
                map.put("Code", 401);
                map.put("Response", "权限不足");
            }
        } else {
            map.put("Code", 401);
            map.put("Response", "Token验证失败");
        }
        return gson.toJson(map);
    }
}