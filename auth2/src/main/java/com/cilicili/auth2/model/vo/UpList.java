package com.cilicili.auth2.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName UpList
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/16 16:20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpList implements Serializable {
    private static final long serialVersionUID = 4610527559815882318L;

    private List<UpListItem> list;

    private Long total;
}
