package com.cilicili.auth2.service.adapter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.DesensitizedUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cilicili.auth2.common.Gender;
import com.cilicili.auth2.model.dto.UserUpdateDto;
import com.cilicili.auth2.model.entity.User;
import com.cilicili.auth2.model.vo.UserAccount;
import com.cilicili.auth2.model.vo.UserBaseInfo;
import com.cilicili.common.model.SafeUser;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName UserAdapter
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/9 15:13
 **/
public class UserAdapter {
    private UserAdapter() {
    }

    private static void desensitization(User user) {
        String phone = user.getPhone();
        String email = user.getEmail();
        if (StringUtils.isNotEmpty(phone)) {
            user.setPhone(DesensitizedUtil.mobilePhone(phone));
        }
        if (StringUtils.isNotEmpty(email)) {
            user.setEmail(DesensitizedUtil.email(email));
        }
    }

    public static SafeUser convertToSafeUser(User user) {
        desensitization(user);
        return BeanUtil.copyProperties(user, SafeUser.class);
    }

    public static UserBaseInfo safeUser2BaseUser(SafeUser safeUser) {
        UserBaseInfo userBaseInfo = BeanUtil.copyProperties(safeUser, UserBaseInfo.class, "birth", "gender");
        Date birthDate = safeUser.getBirth();
        if (birthDate != null) {
            String birth = new SimpleDateFormat("MM-dd").format(birthDate);
            userBaseInfo.setBirth(birth);
        }
        Integer gValue = safeUser.getGender();
        if (gValue != null) {
            String gender = Gender.getName(gValue);
            userBaseInfo.setGender(gender);
        }
        return userBaseInfo;
    }

    public static UserAccount safeUser2UserAccount(SafeUser safeUser) {
        return BeanUtil.copyProperties(safeUser, UserAccount.class);
    }
}
