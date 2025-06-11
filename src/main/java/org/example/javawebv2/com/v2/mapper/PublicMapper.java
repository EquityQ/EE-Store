package org.example.javawebv2.com.v2.mapper;

import org.apache.ibatis.annotations.*;
import org.example.javawebv2.com.v2.Model.MyShopElement;
import org.example.javawebv2.com.v2.Model.element;

import java.util.List;

@Mapper
public interface PublicMapper {
    @Select("SELECT * FROM element")
    List<element> getAllElements();

    @Delete("DELETE FROM element WHERE name = #{name}")
    int deleteElement(@Param("name") String name);

    @Update("UPDATE element SET name= #{name}, price = #{price}, value = #{value}, description = #{description}, image = #{image} WHERE name = #{oldName}")
    int changeElement(String name, double price, int value, String description, String image, String oldName);

    @Insert("INSERT INTO element (name, price, value, description, image) VALUES (#{name}, #{price}, #{value}, #{description}, #{image})")
    int insertElement(String name, double price, int value, String description, String image);
}