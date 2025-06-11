package org.example.javawebv2.com.v2.Service;

import org.example.javawebv2.com.v2.Model.User;
import org.example.javawebv2.com.v2.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class loginService {
    private final LoginMapper loginMapper;

    @Autowired
    public loginService(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    public boolean validateUser(String username, String password) {
        try {
            return loginMapper.validateUser(username, password) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateUser(String username) {
        try {
            return loginMapper.validateUserByUsername(username) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerUser(String username, String password) {
        try {
            return loginMapper.registerUser(username, password) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getPermission(String username) {
        try {
            return loginMapper.getPermission(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> getUserAllList() {
        try {
            return loginMapper.getUserAllList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteUser(String username) {
        try {
            return loginMapper.deleteUser(username) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeUser(String username, String password) {
        try {
            return loginMapper.changeUser(username, password) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}