package org.example.javawebv2.com.v2.mapper;

import org.apache.ibatis.annotations.*;
import org.example.javawebv2.com.v2.Model.User;

import java.util.List;

@Mapper
public interface LoginMapper {
    @Select("SELECT COUNT(*) FROM userinfo WHERE username = #{username} AND password = #{password}")
    int validateUser(@Param("username") String username, @Param("password") String password);

    @Select("SELECT COUNT(*) FROM userinfo WHERE username = #{username}")
    int validateUserByUsername(@Param("username") String username);

    @Insert("INSERT INTO userinfo (username, password) VALUES (#{username}, #{password})")
    int registerUser(@Param("username") String username, @Param("password") String password);

    @Select("SELECT permission FROM userinfo WHERE username = #{username}")
    String getPermission(@Param("username") String username);

    @Select("select * from userinfo")
    @Results({
            @Result(column = "username", property = "name")
    })
    List<User> getUserAllList();

    @Delete("DELETE FROM userinfo WHERE username = #{username}")
    int deleteUser(@Param("username") String username);

    @Update("UPDATE userinfo SET password = #{password} WHERE username = #{username}")
    int changeUser(@Param("username") String username, @Param("password") String password);
}