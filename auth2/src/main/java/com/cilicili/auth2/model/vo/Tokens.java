package com.cilicili.auth2.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName Tokens
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/28 2:30
 **/
@Data
@AllArgsConstructor
public class Tokens implements Serializable {
    private String accessToken;
    private String refreshToken;
    private static final long serialVersionUID = -1710603556702286017L;
}
