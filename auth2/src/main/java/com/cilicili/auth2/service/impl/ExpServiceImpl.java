package com.cilicili.auth2.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cilicili.auth2.mapper.ExpMapper;
import com.cilicili.auth2.model.entity.Exp;
import com.cilicili.auth2.service.ExpService;
import org.springframework.stereotype.Service;

/**
* @author Zhou JunJie
* @description 针对表【exp(用户等级经验表)】的数据库操作Service实现
* @createDate 2023-12-16 00:28:09
*/
@Service
public class ExpServiceImpl extends ServiceImpl<ExpMapper, Exp>
    implements ExpService {

}




