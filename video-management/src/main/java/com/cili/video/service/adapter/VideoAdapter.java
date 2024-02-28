package com.cili.video.service.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.cili.video.model.dto.VideoPublishDto;
import com.cili.video.model.entity.VideoBaseInfo;
import com.cili.video.model.entity.VideoMediaInfo;
import com.cili.video.model.vo.VideoMedia;
import com.cili.video.model.vo.VideoPlayResp;
import com.cilicili.common.common.UserContextHold;
import com.cilicili.common.utils.JsonUtils;

/**
 * @ClassName VideoAdapter
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/29 0:30
 **/
public class VideoAdapter {

    public static VideoBaseInfo buildBaseInfo(VideoPublishDto videoPublishDto) {
        String coverUrl = videoPublishDto.getCoverUrl();
        String title = videoPublishDto.getTitle();
        Long mainPartition = videoPublishDto.getMainPartition();
        Long subPartition = videoPublishDto.getSubPartition();
        List<String> tags = videoPublishDto.getTags();
        String profile = videoPublishDto.getProfile();
        VideoBaseInfo videoBaseInfo = VideoBaseInfo.builder().coverUrl(coverUrl).title(title).mainPartition(mainPartition).subPartition(subPartition)
                .profile(profile).authorId(UserContextHold.getCurrentUser().getId()).build();
        String tagsStr = JsonUtils.toStr(tags);
        videoBaseInfo.setTags(tagsStr);
        return videoBaseInfo;
    }

    public static VideoMediaInfo buildMediaInfo(VideoPublishDto videoPublishDto) {
        String path = videoPublishDto.getPath();
        String fileName = videoPublishDto.getFileName();
        String bucket = videoPublishDto.getBucket();
        Long videoSize = videoPublishDto.getVideoSize();
        String type = fileName.substring(fileName.lastIndexOf('.') + 1);
        return VideoMediaInfo.builder().bucket(bucket)
                .path(path).fileName(fileName).videoSize(videoSize).type(type).build();
    }

    public static VideoPlayResp buildVideoPlayResp(VideoBaseInfo videoBaseInfo, VideoMediaInfo videoMediaInfo) {
        VideoPlayResp videoPlayResp = new VideoPlayResp();
        videoPlayResp.setId(videoBaseInfo.getId());
        videoPlayResp.setTitle(videoBaseInfo.getTitle());
        videoPlayResp.setAuthorId(videoBaseInfo.getAuthorId());
        String tagsStr = videoBaseInfo.getTags();
        videoPlayResp.setTags(JsonUtils.toObject(tagsStr,List.class));
        Date createTime = videoBaseInfo.getCreateTime();
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(createTime);
        videoPlayResp.setCreateTime(time);
        VideoMedia videoMedia = VideoMedia.builder().id(videoMediaInfo.getId()).bucket(videoMediaInfo.getBucket())
                .path(videoMediaInfo.getPath()).videoSize(videoMediaInfo.getVideoSize()).type(videoMediaInfo.getType()).fileName(videoMediaInfo.getFileName()).build();
        videoPlayResp.setVideoMedia(videoMedia);
        return videoPlayResp;
    }
}
