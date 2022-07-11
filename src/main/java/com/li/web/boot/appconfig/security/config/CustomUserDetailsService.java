package com.li.web.boot.appconfig.security.config;

import com.li.web.boot.btuser.entity.Tb_user;
import com.li.web.boot.btuser.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private IUserService iUserService;
    
    /**
     * 需新建配置类注册一个指定的加密方式Bean，或在下一步Security配置类中注册指定
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 通过用户名从数据库获取用户信息
        log.info("用户名:"+username+",准备登录");
        Tb_user tb_user = iUserService.getUserByUsername(username);

        if (tb_user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 得到用户角色
        String role = tb_user.getTbRole();

        // 角色集合
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 角色必须以`ROLE_`开头，数据库中没有，则在这里加
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        return new User(
                tb_user.getTbUsername(),
             // 因为数据库是明文，所以这里需加密密码
                passwordEncoder.encode(tb_user.getTbPassword()),
                authorities
        );
    }
}
