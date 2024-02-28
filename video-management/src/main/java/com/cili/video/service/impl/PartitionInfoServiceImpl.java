package com.cili.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cili.video.mapper.PartitionInfoMapper;
import com.cili.video.model.vo.PartitionList;
import com.cili.video.service.PartitionInfoService;
import com.cili.video.model.entity.PartitionInfo;
import com.cilicili.redisutil.common.CommonRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.cili.video.common.RedisKey.PARTITION_LIST;

/**
* @author Zhou JunJie
* @description 针对表【partition_info(视频分区表)】的数据库操作Service实现
* @createDate 2023-11-29 00:00:07
*/
@Service
public class PartitionInfoServiceImpl extends ServiceImpl<PartitionInfoMapper, PartitionInfo>
    implements PartitionInfoService{

    @Resource
    private CommonRedisTemplate commonRedisTemplate;

    @Override
    public boolean partitionIsExist(Long mainPartition, Long subPartition) {
        PartitionInfo partitionInfo = getById(subPartition);
        return partitionInfo != null && Objects.equals(partitionInfo.getParentId(), mainPartition);
    }

    @Override
    public List<PartitionList> partitionList() {
        List<PartitionList> partitionListsRes = commonRedisTemplate.get(PARTITION_LIST, List.class);
        if(partitionListsRes != null){
            return partitionListsRes;
        }
        LambdaQueryWrapper<PartitionInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PartitionInfo::getParentId,0);
        List<PartitionInfo> list = list(wrapper);
        partitionListsRes = list.stream().map(partitionInfo -> {
            LambdaQueryWrapper<PartitionInfo> childrenWrapper = new LambdaQueryWrapper<>();
            childrenWrapper.eq(PartitionInfo::getParentId, partitionInfo.getId());
            List<PartitionInfo> children = list(childrenWrapper);
            List<PartitionList> partitionLists = partitionList(children);
            PartitionList partitionList = new PartitionList();
            partitionList.setChildren(partitionLists);
            partitionList.setValue(partitionInfo.getId());
            partitionList.setLabel(partitionInfo.getPartitionName());
            return partitionList;
        }).collect(Collectors.toList());
        commonRedisTemplate.set(PARTITION_LIST,partitionListsRes);
        return partitionListsRes;
    }

    public List<PartitionList> partitionList(List<PartitionInfo> partitionInfos){
        ArrayList<PartitionList> partitionLists = new ArrayList<>();
        for (PartitionInfo partitionInfo : partitionInfos) {
            PartitionList partitionList = new PartitionList();
            partitionList.setLabel(partitionInfo.getPartitionName());
            partitionList.setValue(partitionInfo.getId());
            partitionLists.add(partitionList);
        }
        return partitionLists;
    }


}




