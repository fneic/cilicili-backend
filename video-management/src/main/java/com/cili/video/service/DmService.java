package com.cili.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cili.video.model.entity.Dm;
import com.cili.video.model.proto.DmListProto;

/**
* @author Zhou JunJie
* @description 针对表【dm(视频弹幕表)】的数据库操作Service
* @createDate 2023-12-26 01:17:02
*/
public interface DmService extends IService<Dm> {

    /**
     * 获取弹幕片段
     *
     * @param vid
     * @return
     */
    DmListProto.DmList getDmSeg(Long vid);
}
