package com.cilicili.auth2.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName Account
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/21 23:34
 **/
@Data
public class UserAccount implements Serializable {
    private static final long serialVersionUID = 7930354233557227743L;

    private Long id;

    private String nickName;

    private String account;

    private Date birth;

    private String sign;

    private Integer gender;

}
