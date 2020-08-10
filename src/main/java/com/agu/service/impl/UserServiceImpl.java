package com.agu.service.impl;

import com.agu.domain.User;
import com.agu.mapper.UserMapper;
import com.agu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public List<User> findAllUsers() {
        List<User> list = userMapper.findAllUsers();
        return list;
    }

}
