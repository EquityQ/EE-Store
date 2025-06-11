package org.example.javawebv2.com.v2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TokenService(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }
    private void ifExistThenDelete(String username) {
        jdbcTemplate.update("delete from Token where user_id = (select userid from Userinfo where username = ?)", username);
    }

    public boolean insert(String token,String username) {
        ifExistThenDelete(username);
        String userid = jdbcTemplate.queryForObject("select userid from Userinfo where username = ?", String.class, username);
        return jdbcTemplate.update("insert into Token(token,user_id) values(?,?)", token, Integer.valueOf(userid)) > 0;
    }

    public boolean validateToken(String token){
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("select time from Token where token = ?", token);

        if (!rowSet.next()) {
            // 没有查询到 token
            return false;
        }

        // 获取 token 的创建时间
        Timestamp timestamp = rowSet.getTimestamp("time");

        // 获取当前时间
        long now = System.currentTimeMillis();

        // 计算时间差
        long diff = now - timestamp.getTime();
        long days = ChronoUnit.DAYS.between(timestamp.toLocalDateTime(), Timestamp.from(java.time.Instant.now()).toLocalDateTime());

        if (days > 7) {
            // 删除 token
            jdbcTemplate.update("delete from Token where token = ?", token);
            return false;
        } else {
            // 更新时间为现在
            jdbcTemplate.update("update Token set time = now() where token = ?", token);
            return true;
        }
    }
    public String getUsername(String token) {
        return jdbcTemplate.queryForObject("select username from Userinfo where userid = (select user_id from Token where token = ?)", String.class, token);
    }
    public boolean deleteToken(String token) {
        return jdbcTemplate.update("delete from Token where token = ?", token) > 0;
    }
    public String getPermission(String token) {
        String name = getUsername(token);
        return jdbcTemplate.queryForObject("select permission from Userinfo where username = ?", String.class, name);
    }

    public boolean setPermission(String token){
        String name = getUsername(token);
        return jdbcTemplate.update("update Userinfo set permission = 'admin' where username = ?", name) > 0;
    }
}
