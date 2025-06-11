package org.example.javawebv2.com.v2.Service;

import org.example.javawebv2.com.v2.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class loginService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public loginService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean validateUser(String username, String password) {
        String sql = "SELECT COUNT(*) FROM userinfo WHERE username = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username, password}, Integer.class);
        return count != null && count > 0;
    }
    public boolean validateUser(String username) {
        String sql = "SELECT COUNT(*) FROM userinfo WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
        return count != null && count > 0;
    }
    public boolean registerUser(String username, String password){
        String sql = "INSERT INTO userinfo (username, password) VALUES (?, ?)";
        try{
            jdbcTemplate.update(sql, username, password);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public String getPermission(String username) {
        String sql = "SELECT permission FROM userinfo WHERE username = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{username}, String.class);
    }

    public List<User> getUserAllList(){
        return jdbcTemplate.query("select * from userinfo", (rs, rowNum) -> {
            User user = new User();
            user.setName(rs.getString("username"));
            user.setPermission(rs.getString("permission"));
            return user;
        });
    }
    public boolean deleteUser(String username) {
        String userid = jdbcTemplate.queryForObject("select userid from userinfo where username = ?", new Object[]{username}, String.class);
//        删除token中对应的记录
        jdbcTemplate.update("delete from token where user_id = ?", Integer.valueOf(userid));
        String sql = "DELETE FROM userinfo WHERE username = ?";
        try {
            int rowsAffected = jdbcTemplate.update(sql, username);
            if (rowsAffected > 0) {
                return true;
            } else {
                return false; // 没有找到匹配的记录
            }
        } catch (DataAccessException e) {
            // 记录异常信息
            e.printStackTrace();
            // 或者使用日志框架记录日志
            // logger.error("Error deleting user: " + username, e);
            return false;
        }
    }
    public boolean changeUser(String username, String password){
        String sql = "UPDATE userinfo SET password = ? WHERE username = ?";
        try{
            jdbcTemplate.update(sql, password, username);
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
