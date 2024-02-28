package com.cili.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cili.video.model.entity.PartitionInfo;
import com.cili.video.model.vo.PartitionList;
import java.util.List;

/**
* @author Zhou JunJie
* @description 针对表【partition_info(视频分区表)】的数据库操作Service
* @createDate 2023-11-29 00:00:07
*/
public interface PartitionInfoService extends IService<PartitionInfo>{

    /**
     * 检查分区是否存在
     * @param mainPartition 主分区
     * @param subPartition 次分区
     * @return
     */
    boolean partitionIsExist(Long mainPartition, Long subPartition);

    /**
     * 获取分区列表
     * @return
     */
    List<PartitionList> partitionList();
}
