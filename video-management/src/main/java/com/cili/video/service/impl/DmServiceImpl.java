package com.cili.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cili.video.mapper.DmMapper;
import com.cili.video.model.entity.Dm;
import com.cili.video.model.proto.DmListProto;
import com.cili.video.model.proto.DmProto;
import com.cili.video.service.DmService;
import com.cilicili.common.utils.JsonUtils;
import com.cilicili.redisutil.common.CommonRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
* @author Zhou JunJie
* @description 针对表【dm(视频弹幕表)】的数据库操作Service实现
* @createDate 2023-12-26 01:17:02
*/
@Service
public class DmServiceImpl extends ServiceImpl<DmMapper, Dm>
    implements DmService {

    @Autowired
    private CommonRedisTemplate commonRedisTemplate;

    @Override
    public DmListProto.DmList getDmSeg(Long vid, Integer timeStamp) {
        String key = "video:dm:" + vid;
        Long start = timeStamp.longValue();
        Long end = start + 5*60*1000;//获取5分钟内的弹幕
        List<Dm> dmList = commonRedisTemplate.getRangeInZSet(key, start, end, Dm.class);
        if(dmList == null){
            LambdaQueryWrapper<Dm> dmLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dmLambdaQueryWrapper.eq(Dm::getVideoId,vid).between(Dm::getStamp,start,end);
            dmList = list(dmLambdaQueryWrapper);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd hh:mm");
        List<DmProto.Dm> collect = dmList.stream().map(dm ->
                DmProto.Dm.newBuilder().setId(dm.getId()).setContent(dm.getContent())
                        .setColor(dm.getColor()).setCreateTime(simpleDateFormat.format(dm.getCreateTime()))
                        .setFontSize(dm.getFontsize()).setUserId(dm.getUserId()).setVideoId(dm.getVideoId())
                        .setStamp(dm.getStamp()).build()
        ).collect(Collectors.toList());
        return DmListProto.DmList.newBuilder().addAllDmList(collect).build();
    }

    @Override
    public boolean saveDm(Dm dm) {
        return save(dm);
    }

    @Override
    public boolean saveDmToRedis(Dm dm) {
        Long videoId = dm.getVideoId();
        String key = "video:dm:" + videoId;
        String dmStr = JsonUtils.toStr(dm);
        return commonRedisTemplate.addZSet(key,dmStr,dm.getStamp().doubleValue(),1, TimeUnit.DAYS);
    }

}




