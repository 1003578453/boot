package com.li.web.boot.btuser.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.li.web.boot.btuser.entity.Tb_user;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<Tb_user>{

    /**
     * 通过用户名查询用户信息
     * @param username 用户名
     * @return Tb_user
     */
    @Select("SELECT * FROM tb_user WHERE tb_username=#{username}")
    Tb_user getUserByUsername(String username);
}
