package com.cili.video.controller;

import com.cili.video.annotation.NoLogin;
import com.cili.video.model.vo.PartitionList;
import com.cili.video.service.PartitionInfoService;
import com.cilicili.common.resp.BaseResponse;
import com.cilicili.common.utils.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName PartitionController
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/2 23:55
 **/
@RestController
@RequestMapping("/partition")
public class PartitionController {
    @Resource
    private PartitionInfoService partitionInfoService;

    @GetMapping("/list")
    @NoLogin
    public BaseResponse<List<PartitionList>> partitionList(){
        List<PartitionList> partitionLists = partitionInfoService.partitionList();
        return ResultUtils.success(partitionLists);
    }
}
