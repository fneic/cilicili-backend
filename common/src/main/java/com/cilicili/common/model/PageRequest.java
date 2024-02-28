package com.cilicili.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName PageRequest
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/16 16:03
 **/
@Data
public class PageRequest implements Serializable {
    private static final long serialVersionUID = 6612838697056098628L;

    /**
     * 请求页大小
     */
    private Integer ps = 20;

    /**
     * 请求页号
     */
    private Integer pn = 1;

    /**
     * 排序字段
     */
    private String orderType;

}
