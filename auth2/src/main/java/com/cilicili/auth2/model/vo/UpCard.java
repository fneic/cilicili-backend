package com.cilicili.auth2.model.vo;

import com.cilicili.auth2.annotation.NoLogin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName UpCard
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/12/17 14:58
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpCard extends UpInfo implements Serializable {
    private static final long serialVersionUID = -2510589029315188658L;

    private Boolean isFollow;
}
