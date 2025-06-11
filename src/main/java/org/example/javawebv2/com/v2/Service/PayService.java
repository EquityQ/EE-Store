package org.example.javawebv2.com.v2.Service;

import org.example.javawebv2.com.v2.Model.CartItem;
import org.example.javawebv2.com.v2.Model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PayService {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PayService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Orders> getLogs(String username){
        String sql = "select * from orders where username=? order by time desc";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Orders orders = new Orders();
            orders.setId(rs.getLong("id"));
            orders.setOrderId(rs.getString("order_id"));
            orders.setOther(rs.getString("other"));
            orders.setTime(rs.getTimestamp("time"));
            orders.setUsername(rs.getString("username"));
            orders.setValue(rs.getDouble("value"));
            return orders;
        },username);
    }
    public void addLog(String username,double value,String other){
//        生成一个odid 最多32位
        String odid = java.util.UUID.randomUUID().toString().replace("-","").substring(0,32);
        String sql = "insert into orders(order_id,other,username,value) values(?,?,?,?)";
        jdbcTemplate.update(sql,odid,other,username,value);
    }
    public synchronized boolean addvalue(String username, double value) {
        String sql = "update userinfo set value=value+? where username=?";
        return jdbcTemplate.update(sql, value, username) == 1;
    }

    public double getvalue(String username){
        String sql = "select value from userinfo where username=?";
        return jdbcTemplate.queryForObject(sql,Double.class,username);
    }

    public synchronized boolean exchange(String username,double value,String towho){
        if(value <= 0){
            return false;
        }
        if(getvalue(username) < value){
            return false;
        }
        if(addvalue(towho,value)){
            addvalue(username,-value);
            addLog(username,-value,"向（"+towho+"）转账");
            addLog(towho,value,"收到转账（"+username+"）");
            return true;
        }else{
            return false;
        }
    }
    public synchronized boolean payForOrder(String username, List<CartItem> items) {
        if(items.size() == 0){
            return false;
        }
        // 1. 计算订单总价
        double totalAmount = 0;
        StringBuilder logBuilder = new StringBuilder();
        for (CartItem item : items) {
            logBuilder.append(item.getName())
                      .append("*")
                      .append(item.getQuantity())
                      .append(",");
//            数据库查询item价格
            double sql_item_price = jdbcTemplate.queryForObject("select price from element where name=?",Double.class,item.getName());
            item.setPrice(sql_item_price);
            totalAmount += item.getPrice() * item.getQuantity();
        }
//        去除最后一个逗号
        logBuilder.deleteCharAt(logBuilder.length() - 1);
        String log = "购买商品："+logBuilder;
        // 2. 调用支付网关（这里模拟支付）
        boolean paymentSuccessful = processPayment(username, totalAmount);
        if (paymentSuccessful) {
            // 3. 更新订单状态为已支付
//            添加log 要求包含购买的商品信息
            addLog(username,-totalAmount,log);
//            扣减商品库存
            for (CartItem item : items) {
                String sql = "update element set value=value-? where name=?";
                jdbcTemplate.update(sql, item.getQuantity(), item.getName());
            }
        }
        // 3. 返回支付结果
        return paymentSuccessful;
    }

    private boolean processPayment(String username, double amount) {
        // 支付处理逻辑
        if (amount <= 0) {
            return false;
        }
        if(getvalue(username) < amount){
            return false;
        }
        if(addvalue(username,-amount)){
            return true;
        }else{
            return false;
        }
    }
}
