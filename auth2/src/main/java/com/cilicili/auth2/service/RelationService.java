package com.cilicili.auth2.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cilicili.auth2.model.entity.Relation;
import com.cilicili.auth2.model.vo.UpList;
import com.cilicili.common.model.PageRequest;

/**
* @author Zhou JunJie
* @description 针对表【relation(用户关系表)】的数据库操作Service
* @createDate 2023-12-16 00:28:15
*/
public interface RelationService extends IService<Relation> {

    /**
     * 关注up
     * @param fid
     */
    void follow(Long fid);

    /**
     * 当前用户是佛关注该up
     * @param fid
     * @return
     */
    Boolean isFollow(Long fid);

    /**
     * 取消关注
     * @param fid
     */
    void unFollow(Long fid);

    /**
     * 获取关注列表
     *
     * @param pageRequest
     * @return
     */
    UpList listOfFollows(PageRequest pageRequest);
}
