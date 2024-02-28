package com.cili.video;

import com.cili.video.model.entity.VideoBaseInfo;
import com.cili.video.model.proto.DmListProto;
import com.cili.video.model.proto.DmProto;
import com.cili.video.model.vo.InterActive;
import com.cili.video.service.VideoBaseInfoService;
import com.cilicili.common.utils.JsonUtils;
import com.cilicili.redisutil.common.CommonRedisTemplate;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.TextFormat;
import com.google.protobuf.util.JsonFormat;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VideoApplicationTest {

    @Resource
    private CommonRedisTemplate commonRedisTemplate;

    @Test
    void testContext() throws InvalidProtocolBufferException {
        DmProto.Dm.Builder dmBuilder = DmProto.Dm.newBuilder();
        dmBuilder.setContent("你好");
        dmBuilder.setId(1L);
        dmBuilder.setColor("#FFFFFF");
        DmProto.Dm dm = dmBuilder.build();
        dmBuilder = DmProto.Dm.newBuilder();
        DmProto.Dm build = dmBuilder.setContent("哈啊哈").build();
        DmListProto.DmList.Builder dmListBuilder = DmListProto.DmList.newBuilder();
        DmListProto.DmList dmList = dmListBuilder.addDmList(dm).addDmList(build).build();

//        byte[] byteArray = dm.toByteArray();
//        DmProto.Dm dm1 = DmProto.Dm.parseFrom(byteArray);
        byte[] byteArray = dmList.toByteArray();
        DmListProto.DmList dmList1 = DmListProto.DmList.parseFrom(byteArray);
        String print = JsonFormat.printer().print(dmList1);
        System.out.println(print);
//        List<DmProto.Dm> dmListList = dmList1.getDmListList();
//        System.out.println(dmListList);
//        String s = TextFormat.printer().escapingNonAscii(false).printToString(dmListList);
//        System.out.println(s);
    }

}