package com.cilicili.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * @ClassName JsonUtils
 * @Description
 * @Author Zhou JunJie
 * @Date 2023/11/10 1:23
 **/
public class JsonUtils {

    private JsonUtils(){}

    private static final ObjectMapper objMapper = new ObjectMapper();

    public static String toStr(Object obj){
        try {
            if(obj == null){
                return null;
            }
            return objMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static <T> T toObject(String value,Class<T> clazz){
        try {
            if(value == null){
                return null;
            }
            return objMapper.readValue(value,clazz);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static <T> T mapperToObject(Object object, Class<T> clazz){
        try {
            if(object == null){
                return null;
            }
            return objMapper.convertValue(object,clazz);
        } catch (IllegalArgumentException e) {
            throw new UnsupportedOperationException(e);
        }
    }
    public static Map objToMap(Object obj){
        if(obj == null){
            return null;
        }
        return objMapper.convertValue(obj, Map.class);
    }

    public static <T> T mapToObj(Map map,Class<T> clazz){
        if(map == null){
            return null;
        }
        try {
            return objMapper.readValue(objMapper.writeValueAsString(map),clazz);
        } catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    public static byte[] toJsonByte(Object object) {
        try {
            return objMapper.writeValueAsBytes(object);
        }catch (JsonProcessingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
}
