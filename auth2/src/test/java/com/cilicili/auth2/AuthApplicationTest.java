package com.cilicili.auth2;

import cn.hutool.core.bean.BeanUtil;
import com.cilicili.auth2.model.entity.User;
import com.cilicili.auth2.service.adapter.UserAdapter;
import com.cilicili.redisutil.common.CodeManage;
import com.cilicili.redisutil.common.CommonRedisTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@SpringBootTest
class AuthApplicationTest {

    @Resource
    private CommonRedisTemplate commonRedisTemplate;
    @Resource
    private CodeManage codeManage;

    @Test
    void contextLoads() {
        User user = new User();
        user.setId(123L);
        user.setPhone("15284709138");
        user.setEmail("2563811973@qq.com");
        user.setNickName("张三");
        user.setGender(123);
        Map<String, Object> objectMap = BeanUtil.beanToMap(user,false,true);
        System.out.println(objectMap);
        commonRedisTemplate.updateHashAll("testhash",user,10, TimeUnit.MINUTES);
        commonRedisTemplate.setHashAll("testsethash",user,10, TimeUnit.MINUTES);

    }
}