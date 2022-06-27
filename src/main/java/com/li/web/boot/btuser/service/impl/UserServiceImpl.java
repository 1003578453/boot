package com.li.web.boot.btuser.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.li.web.boot.btuser.entity.Tb_user;
import com.li.web.boot.btuser.mapper.UserMapper;
import com.li.web.boot.btuser.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,Tb_user> implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Tb_user getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    @Override
    public String insertUser(Tb_user tb_user) {
        tb_user.setTbPassword(passwordEncoder.encode(tb_user.getTbPassword()));
        int insert = userMapper.insert(tb_user);
        return insert+"";
    }
}
