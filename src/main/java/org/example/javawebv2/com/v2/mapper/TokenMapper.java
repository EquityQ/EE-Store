package org.example.javawebv2.com.v2.mapper;

import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;

@Mapper
public interface TokenMapper {
    @Delete("delete from Token where user_id = (select userid from Userinfo where username = #{username})")
    void ifExistThenDelete(@Param("username") String username);

    @Insert("insert into Token(token,user_id) values(#{token},(select userid from Userinfo where username = #{username}))")
    int insert(@Param("token") String token, @Param("username") String username);

    @Select("select time from Token where token = #{token}")
    Timestamp getTokenTime(@Param("token") String token);

    @Update("update Token set time = now() where token = #{token}")
    int updateTokenTime(@Param("token") String token);

    @Delete("delete from Token where token = #{token}")
    int deleteToken(@Param("token") String token);

    @Select("select username from Userinfo where userid = (select user_id from Token where token = #{token})")
    String getUsername(@Param("token") String token);

    @Select("select permission from Userinfo where username = #{username}")
    String getPermission(@Param("username") String username);

    @Update("update Userinfo set permission = 'admin' where username = #{username}")
    int setPermission(@Param("username") String username);
}