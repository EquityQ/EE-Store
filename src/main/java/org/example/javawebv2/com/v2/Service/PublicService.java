package org.example.javawebv2.com.v2.Service;

import org.example.javawebv2.com.v2.Model.MyShopElement;
import org.example.javawebv2.com.v2.Model.element;
import org.example.javawebv2.com.v2.mapper.PublicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicService {
    private final PublicMapper publicMapper;

    @Autowired
    public PublicService(PublicMapper publicMapper) {
        this.publicMapper = publicMapper;
    }

    public List<element> getAllElements() {
        try {
            return publicMapper.getAllElements();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteElement(String name) {
        try {
            return publicMapper.deleteElement(name) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean changeElement(MyShopElement element) {
        try {
            return publicMapper.changeElement(element.getName(), element.getPrice(), element.getValue(), element.getDescription(), element.getImage(), element.getOldName()) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertElement(MyShopElement element) {
        try {
            return publicMapper.insertElement(element.getName(), element.getPrice(), element.getValue(), element.getDescription(), element.getImage()) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}