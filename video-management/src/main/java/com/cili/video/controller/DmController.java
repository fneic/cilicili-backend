package com.cili.video.controller;

import com.cili.video.annotation.NoLogin;
import com.cili.video.model.proto.DmListProto;
import com.cili.video.service.DmService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName DmController
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/26 22:04
 **/
@RestController
@RequestMapping("/dm")
public class DmController {
    @Resource
    private DmService dmService;

    @GetMapping("/seg")
    @NoLogin
    public void getDms(@RequestParam("vid") Long vid,@RequestParam("timeStamp")Integer timeStamp, HttpServletResponse response) throws IOException {
        DmListProto.DmList dmSeg = dmService.getDmSeg(vid,timeStamp);
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","inline");
        ServletOutputStream outputStream = response.getOutputStream();
        dmSeg.writeTo(outputStream);
    }
}
