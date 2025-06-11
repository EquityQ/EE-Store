package org.example.javawebv2.com.v2.Controller;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.javawebv2.com.v2.Model.*;
import org.example.javawebv2.com.v2.Service.PayService;
import org.example.javawebv2.com.v2.Service.PublicService;
import org.example.javawebv2.com.v2.Service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.example.javawebv2.com.v2.Service.loginService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiRequest {
    private final loginService loginService;
    private final TokenService tokenService;
    private final PublicService elementService;
    private final PayService paymentService;
    @Autowired
    public ApiRequest(loginService loginService, TokenService tokenService, PublicService elementService, PayService paymentService) {
        this.tokenService = tokenService;
        this.loginService = loginService;
        this.elementService = elementService;
        this.paymentService = paymentService;
    }
    @RequestMapping("/captcha")
    public void captcha(HttpServletResponse resp, HttpServletRequest req) throws IOException {
        resp.setContentType("image/jpeg");
        codepic cp = new codepic();
        String code = cp.generateCode();
        req.getSession().setAttribute("code", code);
        cp.createImage(code, resp.getOutputStream());
    }
    @PostMapping("/user/login")
    public String postExample(@RequestBody Mydata data,HttpServletRequest req) {
        String password = data.getPassword();
        String captcha = data.getCaptcha();
        String username = data.getUsername();
        HttpSession session = req.getSession();
        Gson returnData = new Gson();
        Map<String,Object> result = new HashMap<>();
        if(session.getAttribute("code") != null && session.getAttribute("code").equals(captcha)){
            if(loginService.validateUser(username,password)){
                String token = generateToken.Token();
                if(tokenService.insert(token,username)){
                    result.put("Code", 200);
                    result.put("Reason", "登录成功");
                    result.put("token",token);
                    result.put("permission", loginService.getPermission(username));
                    session.setAttribute("token",token);
                }else{
                    result.put("Code", 500);
                    result.put("Reason", "服务器错误");
                }
            }else{
                result.put("Code", 401);
                result.put("Reason", "用户名或密码错误");
            }
        }else{
            result.put("Code", 400);
            result.put("Reason", "验证码错误");
        }
        return returnData.toJson(result);
    }
    @PostMapping("/user/token-auth")
    public String tokenAuth(@RequestHeader String token,HttpServletRequest req) {
        Gson returnData = new Gson();
        Map<String,Object> result = new HashMap<>();
        if(req.getSession().getAttribute("token")!= null&& req.getSession().getAttribute("token").equals(token)){
            result.put("Code", 200);
            result.put("Response", "验证成功");
            result.put("permission",tokenService.getPermission(token));
            return returnData.toJson(result);
        }
        if(tokenService.validateToken(token)){
            result.put("Code", 200);
            result.put("permission",tokenService.getPermission(token));
            result.put("Response", "验证成功");
        }else{
            result.put("Code", 401);
            result.put("Response", "验证失败");
        }
        return returnData.toJson(result);
    }
    @PostMapping("/user/getinfo")
    public String getInfo(@RequestHeader String token) {
        Gson returnData = new Gson();
        Map<String,Object> result = new HashMap<>();
        if(tokenService.validateToken(token)){
            try{
                String username = tokenService.getUsername(token);
                result.put("Code", 200);
                result.put("Response", "查询成功");
                result.put("username",username);
            }catch (Exception e){
                result.put("Code", 500);
                result.put("Response", "服务器错误");
            }
        }else{
            result.put("Code", 401);
            result.put("Response", "验证失败");
        }
        return returnData.toJson(result);
    }
    @PostMapping("/user/logout")
    public String logout(@RequestHeader String token,HttpServletRequest req) {
        Gson returnData = new Gson();
        Map<String,Object> result = new HashMap<>();
        if(tokenService.validateToken(token)){
            if(tokenService.deleteToken(token)){
                result.put("Code", 200);
                result.put("Response", "注销成功");
            }else{
                result.put("Code", 500);
                result.put("Response", "服务器错误");
            }
        }else{
            result.put("Code", 401);
            result.put("Response", "登录态验证失败");
        }
//        清空session
        req.getSession().invalidate();
        return returnData.toJson(result);
    }
    @PostMapping("/user/register")
    public String register(@RequestBody Mydata data,HttpServletRequest req) {
        String password = data.getPassword();
        String captcha = data.getCaptcha();
        String username = data.getUsername();
        HttpSession session = req.getSession();
        Gson returnData = new Gson();
        Map<String,Object> result = new HashMap<>();
        if(session.getAttribute("code") != null && session.getAttribute("code").equals(captcha)){
            if(loginService.registerUser(username,password)){
                String token = generateToken.Token();
                if(tokenService.insert(token,username)){
                    result.put("Code", 200);
                    result.put("permission", loginService.getPermission(username));
                    result.put("token",token);
                    result.put("Reason", "注册成功");
                    session.setAttribute("token",token);
                }
            }else{
                result.put("Code", 500);
                result.put("Reason", "服务器错误");
            }
        }else{
            result.put("Code", 400);
            result.put("Reason", "验证码错误");
        }
        return returnData.toJson(result);
    }
    @RequestMapping("/ip")
    public String ip(HttpServletRequest req) {
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        map.put("Code",200);
        map.put("ip",req.getRemoteAddr());
        return gson.toJson(map);
    }
    @RequestMapping("/element/info")
    public String elementInfo(HttpServletRequest req) {
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        map.put("Code",200);
        List elements = elementService.getAllElements();
        map.put("elements",elements);
        return gson.toJson(map);
    }

    @PostMapping("/user/code")
    public String code(@RequestHeader String token,@RequestHeader String code){
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        if(tokenService.validateToken(token)&&code.equals("EquityQ")){
            if(tokenService.setPermission(token)){
                map.put("Code",200);
                map.put("Response","权限已经提升至超级管理员");
            }else{
                map.put("Code",500);
                map.put("Response","服务器错误");
            }
        }else{
            map.put("Code",401);
            map.put("Response","验证失败");
        }
        return gson.toJson(map);
    }
    @PostMapping("/user/select-info")
    public String selectInfo(@RequestHeader String token) {
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        if(tokenService.validateToken(token)){
            String permission = tokenService.getPermission(token);
            if(permission.equals("admin")){
                map.put("Code",200);
                map.put("users",loginService.getUserAllList());
            }else{
                String username = tokenService.getUsername(token);
                map.put("Code",200);
                List<User> ef = new ArrayList<>();
                User e = new User();
                e.setName(username);
                e.setPermission(permission);
                ef.add(e);
                map.put("users", ef);
            }
        }else{
            map.put("Code",401);
            map.put("Response","验证失败");
        }
        return gson.toJson(map);
    }
    @PostMapping("/user/delete")
    public String deleteUser(@RequestHeader String token,@RequestParam String name) {
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        if(tokenService.validateToken(token)){
            String permission = tokenService.getPermission(token);
            String username = tokenService.getUsername(token);
            if(permission.equals("admin")||name.equals(username)){
                if(loginService.deleteUser(name)){
                    map.put("Code",200);
                    map.put("Response","删除成功");
                }else{
                   map.put("Code",500);
                   map.put("Response","服务器错误");
                }
            }else{
                map.put("Code",401);
                map.put("Response","权限不足");
            }
        }
        return gson.toJson(map);
    }
    @PostMapping("/user/change")
    public String changeUser(@RequestHeader String token,@RequestParam String name,@RequestParam String newpassword) {
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        if(tokenService.validateToken(token)){
            String permission = tokenService.getPermission(token);
            if(permission.equals("admin")||name.equals(tokenService.getUsername(token))){
                String username = tokenService.getUsername(token);
                if(loginService.changeUser(username,newpassword)){
                    map.put("Code",200);
                    map.put("Response","修改成功");
                }else{
                    map.put("Code",500);
                    map.put("Response","服务器错误");
                }
           }else{
                map.put("Code",401);
                map.put("Response","权限不足");
            }
        }
        return gson.toJson(map);
    }
    @PostMapping("/element/delete")
    public String deleteElement(@RequestHeader String token,@RequestParam String name) {
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        if(tokenService.validateToken(token)){
            String permission = tokenService.getPermission(token);
            if(permission.equals("admin")){
                if(elementService.deleteElement(name)){
                    map.put("Code",200);
                    map.put("Response","删除成功");
                }else{
                    map.put("Code",500);
                    map.put("Response","服务器错误");
                }
            }else{
                map.put("Code",401);
                map.put("Response","权限不足");
            }
        }else{
            map.put("Code",401);
            map.put("Response","Token验证失败");
        }
        return gson.toJson(map);
    }
    @RequestMapping("/img/base64")
    public String imgBase64(@RequestParam("file") MultipartFile file) throws IOException {
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        if (file.isEmpty()) {
            map.put("Code",500);
            map.put("Response","文件为空");
            return gson.toJson(map);
        }

        // 读取文件内容
        byte[] bytes = file.getBytes();

        // 将字节数组转换为 Base64 编码的字符串
        String base64String = Base64.getEncoder().encodeToString(bytes);
        base64String = "data:image/jepg;base64," + base64String;


        map.put("Code",200);
        map.put("Response","图片解析成功");
        map.put("base64",base64String);
        return gson.toJson(map);
    }
    @PostMapping("/element/change")
    public String changeElement(@RequestHeader String token,@RequestBody MyShopElement element) {
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        if(tokenService.validateToken(token)){
            String permission = tokenService.getPermission(token);
            if(permission.equals("admin")){
                if(elementService.changeElement(element)){
                    map.put("Code",200);
                    map.put("Response","修改成功");
                }else{
                    map.put("Code",500);
                    map.put("Response","服务器错误");
                }
            }else{
                map.put("Code",401);
                map.put("Response","权限不足");
            }
        }else{
            map.put("Code",401);
            map.put("Response","Token验证失败");
        }
        return gson.toJson(map);
    }
    @PostMapping("/element/insert")
    public String insertElement(@RequestHeader String token,@RequestBody element element) {
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        if(tokenService.validateToken(token)){
            String permission = tokenService.getPermission(token);
            if(permission.equals("admin")){
                if(elementService.insertElement(element)){
                    map.put("Code",200);
                    map.put("Response","添加成功");
                }else{
                    map.put("Code",500);
                    map.put("Response","服务器错误");
                }
            }else{
                map.put("Code",401);
                map.put("Response","权限不足");
            }
        }else{
            map.put("Code",401);
            map.put("Response","Token验证失败");
        }
        return gson.toJson(map);
    }
    @PostMapping("/order/getall")
    public String getAllOrders(@RequestHeader String token) {
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        if(tokenService.validateToken(token)){
            List<Orders> orders = paymentService.getLogs(tokenService.getUsername(token));
            map.put("Code",200);
            map.put("Response","获取成功");
            map.put("Orders",orders);
        }else{
            map.put("Code",401);
            map.put("Response","Token验证失败");
        }
        return gson.toJson(map);
    }
    @PostMapping("/pay/top-up")
    public String topUp(@RequestHeader String token,@RequestParam double value) {
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        if(value <= 0){
            map.put("Code",400);
            map.put("Response","充值金额必须大于0");
            return gson.toJson(map);
        }
        if(tokenService.validateToken(token)){
            String username = tokenService.getUsername(token);
            String permission = tokenService.getPermission(token);
            if(permission.equals("admin")){
                if(paymentService.addvalue(username,value)){
                    map.put("Code",200);
                    map.put("Response","充值成功");
                    paymentService.addLog(username,value,"充值");
                }else{
                    map.put("Code",500);
                    map.put("Response","服务器错误");
                }
            }else{
                map.put("Code",402);
                map.put("Response","权限不足");
            }
        }else{
            map.put("Code",401);
            map.put("Response","Token验证失败");
        }
        return gson.toJson(map);
    }
    @PostMapping("/pay/get")
    public String getValue(@RequestHeader String token) {
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        if(tokenService.validateToken(token)){
            String username = tokenService.getUsername(token);
            double value = paymentService.getvalue(username);
            map.put("Code",200);
            map.put("Response","获取成功");
            map.put("Value",value);
        }else{
            map.put("Code",401);
            map.put("Response","Token验证失败");
        }
        return gson.toJson(map);
    }
    @PostMapping("/pay/exchange")
    public String exchange(@RequestHeader String token,@RequestParam double value,@RequestParam String towho) {
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        //        验证towho是否存在
        if(loginService.validateUser(towho)){
            if(tokenService.validateToken(token)){
                if(paymentService.exchange(tokenService.getUsername(token),value,towho)){
                    map.put("Code",200);
                    map.put("Response","转账成功");
                }else{
                    map.put("Code",500);
                    map.put("Response","参数错误");
                }
            }else{
                map.put("Code",401);
                map.put("Response","Token验证失败");
            }
        }else{
            map.put("Code",404);
            map.put("Response","转账用户不存在");
        }
        return gson.toJson(map);
    }
    @RequestMapping("/pay/pay-order")
    public String payForOrder(@RequestHeader String token,@RequestBody OrderRequest orderRequest) {
        List<CartItem> items = orderRequest.getItems();
        Gson gson = new Gson();
        Map<String,Object> map = new HashMap<>();
        if(tokenService.validateToken(token)){
            String username = tokenService.getUsername(token);
            boolean result = paymentService.payForOrder(username,items);
            if(result){
                map.put("Code",200);
                map.put("Response","支付成功");
            }else{
                map.put("Code",500);
                map.put("Response","支付失败");
            }
        }else{
            map.put("Code",401);
            map.put("Response","Token验证失败");
        }
        return gson.toJson(map);
    }
}
