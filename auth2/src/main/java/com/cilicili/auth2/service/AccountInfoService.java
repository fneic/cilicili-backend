package com.cilicili.auth2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cilicili.auth2.model.entity.AccountInfo;
import com.cilicili.auth2.model.vo.NavStat;

/**
* @author Zhou JunJie
* @description 针对表【account_info(账户信息表)】的数据库操作Service
* @createDate 2023-12-16 00:27:53
*/
public interface AccountInfoService extends IService<AccountInfo> {

    /**
     * 默认保存
     * @return
     */
    boolean saveDefault();

    NavStat state();
}
