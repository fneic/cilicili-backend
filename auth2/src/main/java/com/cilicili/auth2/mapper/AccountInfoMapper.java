package com.cilicili.auth2.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cilicili.auth2.model.entity.AccountInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

/**
* @author Zhou JunJie
* @description 针对表【account_info(账户信息表)】的数据库操作Mapper
* @createDate 2023-12-16 00:27:53
* @Entity generator.domain.AccountInfo
*/
public interface AccountInfoMapper extends BaseMapper<AccountInfo> {

    /**
     * 插入默认值
     * @return
     */
    @Insert("INSERT INTO account_info    VALUES();")
    boolean saveDefault();

}




