package com.cilicili.auth2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cilicili.auth2.mapper.RelationMapper;
import com.cilicili.auth2.model.entity.Relation;
import com.cilicili.auth2.model.vo.UpList;
import com.cilicili.auth2.model.vo.UpListItem;
import com.cilicili.auth2.service.RelationService;
import com.cilicili.auth2.service.UserService;
import com.cilicili.common.common.UserContextHold;
import com.cilicili.common.exception.ThrowUtils;
import com.cilicili.common.model.PageRequest;
import com.cilicili.common.model.SafeUser;
import com.cilicili.common.resp.StatusCode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Zhou JunJie
* @description 针对表【relation(用户关系表)】的数据库操作Service实现
* @createDate 2023-12-16 00:28:15
*/
@Service
public class RelationServiceImpl extends ServiceImpl<RelationMapper, Relation>
    implements RelationService {

    @Resource
    private UserService userService;

    @Override
    public void follow(Long fid) {
        boolean exist = userService.isExist(fid);
        ThrowUtils.throwIf(!exist, StatusCode.OPERATION_FAILED,"所关注用户不存在！");
        Long uid = UserContextHold.getCurrentUser().getId();
        //如果没有关注则关注
        if(Boolean.FALSE.equals(isFollow(fid))) {
            Relation relation = new Relation(fid, uid);
            boolean isSave = save(relation);
            ThrowUtils.throwIf(!isSave, StatusCode.OPERATION_FAILED);
        }
    }

    @Override
    public Boolean isFollow(Long fid) {
        return getRelation(fid) != null;
    }

    @Override
    public void unFollow(Long fid) {
        Relation relation = getRelation(fid);
        if(relation != null){
            removeById(relation);
        }
    }

    @Override
    public UpList listOfFollows(PageRequest pageRequest) {
        Long uid = UserContextHold.getUid();
        LambdaQueryWrapper<Relation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Relation::getFollowing,uid);
        Integer ps = pageRequest.getPs();
        Integer pn = pageRequest.getPn();
        Page<Relation> relationPage = new Page<>(pn,ps);
        page(relationPage,wrapper);
        List<UpListItem> upList = relationPage.getRecords().stream().map(relation -> {
            Long fid = relation.getFollower();
            SafeUser safeUser = userService.getUserById(fid);
            return BeanUtil.copyProperties(safeUser, UpListItem.class);
        }).collect(Collectors.toList());
        return new UpList(upList, relationPage.getTotal());
    }

    @Override
    public List<Long> getFollowings(Long uid) {
        LambdaQueryWrapper<Relation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Relation::getFollower,uid);
        List<Relation> list = list(wrapper);
        return list.stream().map(Relation::getFollowing).collect(Collectors.toList());
    }

    private Relation getRelation(Long fid){
        LambdaQueryWrapper<Relation> wrapper = new LambdaQueryWrapper<>();
        SafeUser currentUser = UserContextHold.getCurrentUser();
        if(currentUser == null){
            return null;
        }
        Long uid = currentUser.getId();
        wrapper.eq(Relation::getFollower,fid).eq(Relation::getFollowing,uid);
        return getOne(wrapper);
    }
}




