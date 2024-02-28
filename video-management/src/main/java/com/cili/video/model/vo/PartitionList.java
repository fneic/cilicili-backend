package com.cili.video.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName PartitionList
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/3 0:03
 **/
@Data
public class PartitionList implements Serializable {

    /**
     * 分区id
     */
    private Long value;

    /**
     * 分区名
     */
    private String label;

    /**
     * 二级分区
     */
    private List<PartitionList> children;



    private static final long serialVersionUID = 5686709597517886777L;
}
