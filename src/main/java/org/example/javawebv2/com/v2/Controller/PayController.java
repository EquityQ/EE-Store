package org.example.javawebv2.com.v2.Controller;

import com.google.gson.Gson;
import org.example.javawebv2.com.v2.Model.CartItem;
import org.example.javawebv2.com.v2.Model.OrderRequest;
import org.example.javawebv2.com.v2.Model.Orders;
import org.example.javawebv2.com.v2.Service.PayService;
import org.example.javawebv2.com.v2.Service.TokenService;
import org.example.javawebv2.com.v2.Service.loginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pay")
public class PayController {
    private final PayService paymentService;
    private final TokenService tokenService;
    private final loginService loginService;

    @Autowired
    public PayController(PayService paymentService, TokenService tokenService, loginService loginService) {
        this.paymentService = paymentService;
        this.tokenService = tokenService;
        this.loginService = loginService;
    }

    @PostMapping("/getall")
    public String getAllOrders(@RequestHeader String token) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        if (tokenService.validateToken(token)) {
            List<Orders> orders = paymentService.getLogs(tokenService.getUsername(token));
            map.put("Code", 200);
            map.put("Response", "获取成功");
            map.put("Orders", orders);
        } else {
            map.put("Code", 401);
            map.put("Response", "Token验证失败");
        }
        return gson.toJson(map);
    }

    @PostMapping("/top-up")
    public String topUp(@RequestHeader String token, @RequestParam double value) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        if (value <= 0) {
            map.put("Code", 400);
            map.put("Response", "充值金额必须大于0");
            return gson.toJson(map);
        }
        if (tokenService.validateToken(token)) {
            String username = tokenService.getUsername(token);
            String permission = tokenService.getPermission(token);
            if (permission.equals("admin")) {
                if (paymentService.addvalue(username, value)) {
                    map.put("Code", 200);
                    map.put("Response", "充值成功");
                    paymentService.addLog(username, value, "充值");
                } else {
                    map.put("Code", 500);
                    map.put("Response", "服务器错误");
                }
            } else {
                map.put("Code", 402);
                map.put("Response", "权限不足");
            }
        } else {
            map.put("Code", 401);
            map.put("Response", "Token验证失败");
        }
        return gson.toJson(map);
    }

    @PostMapping("/get")
    public String getValue(@RequestHeader String token) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        if (tokenService.validateToken(token)) {
            String username = tokenService.getUsername(token);
            double value = paymentService.getvalue(username);
            map.put("Code", 200);
            map.put("Response", "获取成功");
            map.put("Value", value);
        } else {
            map.put("Code", 401);
            map.put("Response", "Token验证失败");
        }
        return gson.toJson(map);
    }

    @PostMapping("/exchange")
    public String exchange(@RequestHeader String token, @RequestParam double value, @RequestParam String towho) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        if (loginService.validateUser(towho)) {
            if (tokenService.validateToken(token)) {
                if (paymentService.exchange(tokenService.getUsername(token), value, towho)) {
                    map.put("Code", 200);
                    map.put("Response", "转账成功");
                } else {
                    map.put("Code", 500);
                    map.put("Response", "参数错误");
                }
            } else {
                map.put("Code", 401);
                map.put("Response", "Token验证失败");
            }
        } else {
            map.put("Code", 404);
            map.put("Response", "转账用户不存在");
        }
        return gson.toJson(map);
    }

    @PostMapping("/pay-order")
    public String payForOrder(@RequestHeader String token, @RequestBody OrderRequest orderRequest) {
        List<CartItem> items = orderRequest.getItems();
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        if (tokenService.validateToken(token)) {
            String username = tokenService.getUsername(token);
            boolean result = paymentService.payForOrder(username, items);
            if (result) {
                map.put("Code", 200);
                map.put("Response", "支付成功");
            } else {
                map.put("Code", 500);
                map.put("Response", "支付失败");
            }
        } else {
            map.put("Code", 401);
            map.put("Response", "Token验证失败");
        }
        return gson.toJson(map);
    }
}