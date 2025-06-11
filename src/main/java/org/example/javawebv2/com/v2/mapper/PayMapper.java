package org.example.javawebv2.com.v2.mapper;

import org.apache.ibatis.annotations.*;
import org.example.javawebv2.com.v2.Model.CartItem;
import org.example.javawebv2.com.v2.Model.Orders;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface PayMapper {
    @Select("select * from orders where username=#{username} order by time desc")
        //    将sql中order_id映射到Orders文件中的OrderId
    @Results({
            @Result(column = "order_id", property = "orderId"),
    })
    List<Orders> getLogs(@Param("username") String username);

    @Insert("insert into orders(order_id,other,username,value) values(#{odid},#{other},#{username},#{value})")
    void addLog(@Param("odid") String odid,@Param("username") String username, @Param("value") double value, @Param("other") String other);

    @Update("update userinfo set value=value+#{value} where username=#{username}")
    int addvalue(@Param("username") String username, @Param("value") double value);

    @Select("select value from userinfo where username=#{username}")
    double getvalue(@Param("username") String username);

    @Update("update element set value=value-#{quantity} where name=#{name}")
    int updateElementStock(@Param("quantity") int quantity, @Param("name") String name);

    @Select("select price from element where name=#{name}")
    double getElementPrice(@Param("name") String name);
}