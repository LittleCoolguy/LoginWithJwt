package com.gao.jwtdemo.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.gao.jwtdemo.Utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.naming.AuthenticationException;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: http://localhost:8080/user/login?username=xiao&password=123
 * @author: XiaoGao
 * @time: 2022/6/11 13:35
 */
@RestController
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public Map<String, Object> userLogin(String username, String password) {
        Map<String, Object> map = new HashMap<>();

        try {

            Map<String, String> payload = new HashMap<>();
            payload.put("username", username);
            payload.put("password", password);
            String token = JwtUtils.getToken(payload);

            map.put("state", true);
            map.put("msg", "登录成功");
            map.put("token", token);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("state", false);
            map.put("msg", e.getMessage());
            map.put("token", "");
        }
        return map;
    }

    @GetMapping("/test")
    public Map<String, Object> test(HttpServletRequest request) {
        String token = request.getHeader("token");
        DecodedJWT verify = JwtUtils.verify(token);
        String id = verify.getClaim("username").asString();
        String name = verify.getClaim("password").asString();
        log.info("用户名：{}", id);
        log.info("密码: {}", name);

        Map<String, Object> map = new HashMap<>();
        map.put("state", true);
        map.put("msg", "请求成功");
        return map;
    }

}
