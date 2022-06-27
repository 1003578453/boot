package com.li.web.boot.btuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.li.web.boot.btuser.entity.Tb_user;

public interface IUserService extends IService<Tb_user> {
    /**
     * 通过用户名获取用户信息
     * @param username 用户名
     */
    Tb_user getUserByUsername(String username);

    /**
     * 新增一条用户数据
     * @param tb_user 用户实体类
     */
    String insertUser(Tb_user tb_user);
}
