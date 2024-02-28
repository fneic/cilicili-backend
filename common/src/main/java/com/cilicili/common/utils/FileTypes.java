package com.cilicili.common.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName FileTypes
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/29 16:11
 **/
public class FileTypes {
    private static final Set<String> VIDEO_TYPES;

    private static final Set<String> PICTURE_TYPES;

    static {
        VIDEO_TYPES = new HashSet<>();
        Collections.addAll(VIDEO_TYPES,"mp4","rmvb","mkv","avi");
        PICTURE_TYPES = new HashSet<>();
        Collections.addAll(PICTURE_TYPES,"jpg","jpeg","png","svg");
    }

    public static boolean videoTypeCheck(String type){
        return VIDEO_TYPES.contains(type);
    }

    public static boolean pictureTypeCheck(String type){
        return PICTURE_TYPES.contains(type);
    }

}
