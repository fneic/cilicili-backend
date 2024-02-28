package com.cili.video.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName VideoMedia
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/11 15:01
 **/
@Data
@Builder
public class VideoMedia implements Serializable {
    private static final long serialVersionUID = -4503797688612461890L;
    /**
     * id
     */
    private Long id;

    /**
     * 视频id
     */
    private Long videoId;

    /**
     * 存储路径
     */
    private String path;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 存储桶
     */
    private String bucket;

    /**
     *  单位 byte
     */
    private Long videoSize;



}
