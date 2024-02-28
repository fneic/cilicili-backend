package com.cili.video.model.dto;

import lombok.Data;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @ClassName VideoUploadDto
 * @Description 视频上传信息
 * @Author Zhou JunJie
 * @Date 2023/11/28 23:29
 **/
@Data
public class VideoPublishDto implements Serializable {
    /**
     * 封面
     */
    @NotBlank(message = "封面不能为空")
    private String coverUrl;

    /**
     *  标题
     */
    @NotBlank(message = "标题不能为空")
    private String title;

    /**
     * 主分区
     */
    @NotNull(message = "未选择分区")
    private Long mainPartition;

    /**
     * 子分区
     */
    @NotNull(message = "未选择分区")
    private Long subPartition;

    /**
     * 标签
     */
    @NotEmpty(message = "标签不能为空")
    private List<String> tags;

    /**
     * 简介
     */
    @NotBlank(message = "简介不能为空")
    private String profile;

    /**
     * 存储路径
     */
    @NotBlank(message = "存储路径不能为空")
    private String path;

    /**
     * 文件名
     */
    @NotBlank(message = "文件名不能为空")
    private String fileName;


    /**
     * 存储桶
     */
    @NotBlank(message = "存储域不能为空")
    private String bucket;

    /**
     *  单位 byte
     */
    @NotNull(message = "文件大小不能为空")
    @Min(value = 1L,message = "上传文件过小")
    private Long videoSize;


    private static final long serialVersionUID = -581478365590845031L;
}
