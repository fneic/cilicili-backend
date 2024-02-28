package com.cili.video.common;

/**
 * redis key
 */
public interface RedisKey {

    /**
     * 视频分区列表
     */
    String PARTITION_LIST = "video:partition:list";

    /**
     * 视频点赞、投币等信息
     */
    String VIDEO_INTERACTION_RECORD = "video:interaction:record:";

    /**
     * 视频点赞、投币等信息同步数据库
     */
    String VIDEO_INTERACTION_RECORD_SYNC = "video:interaction:record:sync";

    /**
     * 用户对视频的互动状态
     */
    String VIDEO_INTERACTION_ACTIVE = "video:interaction:active:";

    /**
     * 用户对视频的互动状态同步数据库
     */
    String VIDEO_INTERACTION_ACTIVE_SYNC = "video:interaction:active:sync";
}
