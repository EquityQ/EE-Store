package org.example.javawebv2.com.v2.Service;

import org.example.javawebv2.com.v2.Model.MyShopElement;
import org.example.javawebv2.com.v2.Model.element;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Service
public class PublicService {

    private JdbcTemplate jdbcTemplate;

    public PublicService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<element> getAllElements() {
        List<element> list = jdbcTemplate.query("SELECT * FROM element", new ElementRowMapper());
        return list;
    }

    private static class ElementRowMapper implements RowMapper<element> {
        @Override
        public element mapRow(ResultSet rs, int rowNum) throws SQLException {
            element element = new element();
            element.setName(rs.getString("name"));
            element.setPrice(rs.getDouble("price"));
            element.setValue(rs.getInt("value"));
            element.setDescription(rs.getString("description"));
            element.setImage(rs.getString("image"));
            return element;
        }
    }

    public boolean deleteElement(String name){
        return jdbcTemplate.update("DELETE FROM element WHERE name = ?", name) > 0;
    }
    public boolean changeElement(MyShopElement element){
        return jdbcTemplate.update("UPDATE element SET name= ?, price = ?, value = ?, description = ?, image = ? WHERE name = ?",
                element.name,element.price, element.value, element.description, element.image, element.oldName) > 0;
    }
    public boolean insertElement(element element){
        return jdbcTemplate.update("INSERT INTO element (name, price, value, description, image) VALUES (?, ?, ?, ?, ?)",
                element.name,element.price, element.value, element.description, element.image) > 0;
    }
}
