package com.cili.video.websocket;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName Msg
 * @Description
 * @Author Zhou JunJie
 * @Date 2024/3/3 22:22
 **/
@Data
public class Msg implements Serializable {

    private String type;
    private String message;

    private static final long serialVersionUID = -2569644345544496443L;
}
