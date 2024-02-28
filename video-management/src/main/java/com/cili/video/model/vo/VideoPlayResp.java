package com.cili.video.model.vo;

import com.cilicili.common.model.SafeUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ClassName VideoInfo
 * @Description 视频的基本信息
 * @Author Zhou JunJie
 * @Date 2023/12/11 14:55
 **/
@Data
public class VideoPlayResp implements Serializable {
    private static final long serialVersionUID = 2564657445120688850L;
    private Long id;
    /**
     * 标题
     */
    private String title;

    /**
     * 上传时间
     */
    private String createTime;

    /**
     * 视频信息
     */
    private VideoMedia videoMedia;

    /**
     * 作者信息
     */
    private Long authorId;

    /**
     * 标签
     */
    private List<String> tags;
}
