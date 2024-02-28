package com.cili.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cili.video.model.entity.VideoBaseInfo;
import com.cili.video.service.VideoBaseInfoService;
import com.cili.video.mapper.VideoBaseInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author Zhou JunJie
* @description 针对表【video_base_info(视频基本信息表)】的数据库操作Service实现
* @createDate 2023-11-28 23:18:43
*/
@Service
public class VideoBaseInfoServiceImpl extends ServiceImpl<VideoBaseInfoMapper, VideoBaseInfo>
    implements VideoBaseInfoService {

}




