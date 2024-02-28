package com.cilicili.auth2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cilicili.auth2.mapper.AccountInfoMapper;
import com.cilicili.auth2.model.entity.AccountInfo;
import com.cilicili.auth2.model.vo.NavStat;
import com.cilicili.auth2.service.AccountInfoService;
import com.cilicili.common.common.UserContextHold;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Zhou JunJie
* @description 针对表【account_info(账户信息表)】的数据库操作Service实现
* @createDate 2023-12-16 00:27:53
*/
@Service
public class AccountInfoServiceImpl extends ServiceImpl<AccountInfoMapper, AccountInfo>
    implements AccountInfoService {

    @Resource
    private AccountInfoMapper accountInfoMapper;

    @Override
    public boolean saveDefault() {
        return accountInfoMapper.saveDefault();
    }

    @Override
    public NavStat state() {
        Long accountInfoId = UserContextHold.getCurrentUser().getAccountInfoId();
        AccountInfo accountInfo = getById(accountInfoId);
        //todo 后期添加动态数
        return NavStat.builder().follower(accountInfo.getFollower()).following(accountInfo.getFollowing()).dynamic(0).build();
    }
}




