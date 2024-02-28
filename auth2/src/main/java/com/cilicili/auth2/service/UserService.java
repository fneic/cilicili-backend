package com.cilicili.auth2.service;


import com.cilicili.auth2.model.dto.UserLoginDto;
import com.cilicili.auth2.model.dto.UserUpdateDto;
import com.cilicili.auth2.model.vo.*;
import com.cilicili.common.model.SafeUser;

/**
* @author Zhou JunJie
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-11-28 00:48:23
*/
public interface UserService {

    /**
     * 用户登录
     *
     * @param userLoginDto 登录参数
     * @return
     */
    Tokens login(UserLoginDto userLoginDto);

    /**
     * 当前用户信息
     *
     * @return
     */
    UserBaseInfo currentUser();

    SafeUser getUserById(Long uid);

    /**
     * 刷新token
     * @param uid
     * @return
     */
    Tokens refresh(Long uid);

    /**
     * 用户是否存在
     * @param uid
     * @return
     */
    boolean isExist(Long uid);

    /**
     * 获取用户账户信息
     * @param uid 用户id
     * @return
     */
    UpInfo upInfo(Long uid);


    NavUserInfo userInfo();

    /**
     * 获取up卡片
     * @param uid
     * @return
     */
    UpCard getUpCard(Long uid);

    /**
     * 用户修改信息
     * @param userUpdateDto
     */
    void updateUserInfo(UserUpdateDto userUpdateDto);

    /**
     * 获取用户的信息
     * @return
     */
    UserAccount getAccount();


    void updateAccount(UserAccount userAccount);
}
