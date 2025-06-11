package org.example.javawebv2.com.v2.Service;

import org.example.javawebv2.com.v2.Model.CartItem;
import org.example.javawebv2.com.v2.Model.Orders;
import org.example.javawebv2.com.v2.mapper.PayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PayService {
    private final PayMapper payMapper;

    @Autowired
    public PayService(PayMapper payMapper) {
        this.payMapper = payMapper;
    }

    public List<Orders> getLogs(String username) {
        try {
            return payMapper.getLogs(username);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addLog(String username, double value, String other) {
        try {
            String odid = java.util.UUID.randomUUID().toString().replace("-", "").substring(0, 32);
            payMapper.addLog(odid,username, value, other);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public boolean addvalue(String username, double value) {
        try {
            return payMapper.addvalue(username, value) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getvalue(String username) {
        try {
            return payMapper.getvalue(username);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Transactional
    public boolean exchange(String username, double value, String towho) {
        if (value <= 0 || getvalue(username) < value) {
            return false;
        }
        try {
            if (addvalue(towho, value)) {
                addvalue(username, -value);
                addLog(username, -value, "向（" + towho + "）转账");
                addLog(towho, value, "收到转账（" + username + "）");
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean payForOrder(String username, List<CartItem> items) {
        if (items.isEmpty()) {
            return false;
        }
        double totalAmount = 0;
        StringBuilder logBuilder = new StringBuilder();
        for (CartItem item : items) {
            logBuilder.append(item.getName()).append("*").append(item.getQuantity()).append(",");
            double sql_item_price = payMapper.getElementPrice(item.getName());
            item.setPrice(sql_item_price);
            totalAmount += item.getPrice() * item.getQuantity();
        }
        logBuilder.deleteCharAt(logBuilder.length() - 1);
        String log = "购买商品：" + logBuilder;

        if (processPayment(username, totalAmount)) {
            addLog(username, -totalAmount, log);
            for (CartItem item : items) {
                payMapper.updateElementStock(item.getQuantity(), item.getName());
            }
            return true;
        } else {
            return false;
        }
    }

    private boolean processPayment(String username, double amount) {
        if (amount <= 0 || getvalue(username) < amount) {
            return false;
        }
        return addvalue(username, -amount);
    }
}