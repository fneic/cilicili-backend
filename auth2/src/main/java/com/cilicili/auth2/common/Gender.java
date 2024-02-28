package com.cilicili.auth2.common;

import java.util.Objects;

/**
 * @author Zhou JunJie
 */

public enum Gender {
    SECRET(0,"秘密"),
    BOY(1,"男"),
    GIRL(2,"女");

    private final Integer value;
    private final String name;

    Gender(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public static String getName(Integer value){
        for (Gender gender : Gender.values()) {
            if(Objects.equals(gender.value, value)){
                return gender.name;
            }
        }
        return "";
    }
}
