package com.cili.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cili.video.mapper.DmMapper;
import com.cili.video.model.entity.Dm;
import com.cili.video.model.proto.DmListProto;
import com.cili.video.model.proto.DmProto;
import com.cili.video.service.DmService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Zhou JunJie
* @description 针对表【dm(视频弹幕表)】的数据库操作Service实现
* @createDate 2023-12-26 01:17:02
*/
@Service
public class DmServiceImpl extends ServiceImpl<DmMapper, Dm>
    implements DmService {

    @Override
    public DmListProto.DmList getDmSeg(Long vid) {
        LambdaQueryWrapper<Dm> dmLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dmLambdaQueryWrapper.eq(Dm::getVideoId,vid);
        List<Dm> dmList = list(dmLambdaQueryWrapper);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd hh:mm");
        List<DmProto.Dm> collect = dmList.stream().map(dm ->
                DmProto.Dm.newBuilder().setId(dm.getId()).setContent(dm.getContent())
                        .setColor(dm.getColor()).setCreateTime(simpleDateFormat.format(dm.getCreateTime()))
                        .setFontSize(dm.getFontsize()).setUserId(dm.getUserId()).setVideoId(dm.getVideoId())
                        .setStamp(dm.getStamp()).build()
        ).collect(Collectors.toList());
        return DmListProto.DmList.newBuilder().addAllDmList(collect).build();
    }

}




