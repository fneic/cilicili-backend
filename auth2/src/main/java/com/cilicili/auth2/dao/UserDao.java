package com.cilicili.auth2.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cilicili.auth2.mapper.UserMapper;
import com.cilicili.auth2.model.entity.User;
import org.springframework.stereotype.Component;

/**
 * @ClassName UserDao
 * @Description 实现 UserService 对数据库的操作
 * @Author Zhou JunJie
 * @Date 2023/11/9 0:52
 **/
@Component
public class UserDao extends ServiceImpl<UserMapper, User> {

    /**
     *  根据手机号查找用户
     * @param phone 手机号
     * @return
     */
    public User getUserByPhone(String phone){
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getPhone,phone);
        return getOne(userLambdaQueryWrapper);
    }

    /**
     *  根据账号查找用户
     * @param account 账号
     * @return
     */
    public User getUserByAccount(String account) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getAccount,account);
        return getOne(userLambdaQueryWrapper);
    }
}
