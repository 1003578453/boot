package com.li.web.boot.btuser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@ToString
public class Tb_user {
    private Integer id;
    private String tbUsername;
    private String tbPassword;
    private String tbRole;

}
