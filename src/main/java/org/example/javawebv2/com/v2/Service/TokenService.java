package org.example.javawebv2.com.v2.Service;

import org.example.javawebv2.com.v2.mapper.TokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {
    private final TokenMapper tokenMapper;

    @Autowired
    public TokenService(TokenMapper tokenMapper) {
        this.tokenMapper = tokenMapper;
    }

    public boolean insert(String token, String username) {
        try {
            tokenMapper.ifExistThenDelete(username);
            return tokenMapper.insert(token, username) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateToken(String token) {
        try {
            Timestamp timestamp = tokenMapper.getTokenTime(token);
            if (timestamp == null) {
                return false;
            }

            long now = System.currentTimeMillis();
            long diff = now - timestamp.getTime();
            long days = ChronoUnit.DAYS.between(timestamp.toLocalDateTime(), Timestamp.from(java.time.Instant.now()).toLocalDateTime());

            if (days > 7) {
                tokenMapper.deleteToken(token);
                return false;
            } else {
                tokenMapper.updateTokenTime(token);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUsername(String token) {
        try {
            return tokenMapper.getUsername(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteToken(String token) {
        try {
            return tokenMapper.deleteToken(token) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getPermission(String token) {
        try {
            String username = getUsername(token);
            return tokenMapper.getPermission(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean setPermission(String token) {
        try {
            String username = getUsername(token);
            return tokenMapper.setPermission(username) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}