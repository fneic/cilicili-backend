package com.cilicili.auth2.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 账户信息表
 * @TableName account_info
 */
@TableName(value ="account_info")
@Data
public class AccountInfo implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 硬币数
     */
    private Integer coins;

    /**
     * 粉丝数
     */
    private Integer follower;

    /**
     * 关注数
     */
    private Integer following;

    /**
     * 获赞数
     */
    private Integer likes;

    /**
     * 播放数
     */
    private Integer plays;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public static AccountInfo getDefault(){
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setCoins(0);
        accountInfo.setFollowing(0);
        accountInfo.setFollower(0);
        accountInfo.setPlays(0);
        accountInfo.setLikes(0);
        return accountInfo;
    }
}