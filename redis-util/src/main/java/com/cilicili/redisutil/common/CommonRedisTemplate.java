package com.cilicili.redisutil.common;



import cn.hutool.core.bean.BeanUtil;
import com.cilicili.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisUtils
 * @Description 操作redis工具
 * @Author Zhou JunJie
 * @Date 2023/11/10 0:57
 **/
@Slf4j
public class CommonRedisTemplate {


    private  final StringRedisTemplate redisTemplate;

    private static final String LOG_TEMPLATE = "Redis Operation Failed:{}\n{}";

    public CommonRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /**
     * 添加键值对
     * @param key 键
     * @param value 值
     * @return 成功？
     */
    public  boolean set(String key,Object value){
        try {
            String strValue = JsonUtils.toStr(value);
            redisTemplate.opsForValue().set(key, strValue);
            return true;
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return false;
        }
    }

    /**
     * 添加键值对
     * @param key 键
     * @return 成功？
     */
    public  <T> T get(String key,Class<T> clazz){
        try {
            String value = redisTemplate.opsForValue().get(key);
            return JsonUtils.toObject(value,clazz);
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return null;
        }
    }

    public boolean remove(String key){
        try {
            return Boolean.TRUE.equals(redisTemplate.delete(key));
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return false;
        }
    }

    /**
     * 设置key的过期时间
     * @param key 键
     * @param time 过期时间
     * @param timeUnit 单位
     * @return 成功？
     */
    public  boolean expire(String key, long time, TimeUnit timeUnit){
        try {
            redisTemplate.expire(key,time,timeUnit);
            return true;
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return false;
        }
    }

    /**
     * 设置key的过期时间
     * @param key 键
     * @param time 过期时间 seconds
     * @return 成功？
     */
    public  boolean expire(String key, long time){
        try {
            redisTemplate.expire(key,time,TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return false;
        }
    }

    /**
     *
     * @param key
     * @param value
     * @param time 过期时间 time <= 0时，return false
     * @return
     */
    public  Boolean set(String key, Object value , long time) {
        try {
            String strValue = objToStr(value);
            redisTemplate.opsForValue().set(key, strValue,time,TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return false;
        }
    }

    public  Boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            String strValue = objToStr(value);
            redisTemplate.opsForValue().set(key, strValue,time,timeUnit);
            return true;
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return false;
        }
    }

    public  <T> T getHashObj(String key,Class<T> clazz){
        try {
            Set<Object> hashKeys = redisTemplate.opsForHash().keys(key);
            List<Object> hashKeysList = new ArrayList<>(hashKeys);
            List<Object> values = redisTemplate.opsForHash().multiGet(key, hashKeysList);
            Map<Object, Object> objectMap = new HashMap<>();
            for (int i = 0; i < values.size(); i++) {
                objectMap.put(hashKeysList.get(i),values.get(i));
            }
            if(objectMap.isEmpty()){
                return null;
            }
            return JsonUtils.mapToObj(objectMap,clazz);
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return null;
        }
    }
    /**
     * 设置属性值，null属性也会保存
     * @param key
     * @param obj 设置的属性对象
     * @param time
     * @param timeUnit
     * @return
     */
    public  boolean setHashAll(String key, Object obj, long time, TimeUnit timeUnit){
        try {
            Map<String, Object> objectMap = objToJsonMap(obj, false);
            redisTemplate.opsForHash().putAll(key,objectMap);
            expire(key,time,timeUnit);
            return true;
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return false;
        }
    }

    /**
     * 设置属性值，null属性也会保存
     * @param key
     * @param obj 设置的属性对象
     */
    public  boolean setHashAll(String key, Object obj){
        try {
            Map<String, Object> objectMap = objToJsonMap(obj, false);
            redisTemplate.opsForHash().putAll(key,objectMap);
            return true;
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return false;
        }
    }

    /**
     * 设置哈希中某个属性值
     * @param key
     * @param hashKey
     * @param value
     * @return
     */
    public  boolean setHashItem(String key, Object hashKey,Object value){
        try {
            redisTemplate.opsForHash().put(key,hashKey,value);
            return true;
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return false;
        }
    }

    /**
     * 增加哈希中某个属性值
     * @param key
     * @param hashKey
     * @return
     */
    public  boolean increaseHashItem(String key, Object hashKey){
        try {
            redisTemplate.opsForHash().increment(key,hashKey,1L);
            return true;
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return false;
        }
    }

    /**
     * 减少哈希中某个属性值
     * @param key
     * @param hashKey
     * @return
     */
    public  boolean decreaseHashItem(String key, Object hashKey){
        try {
            redisTemplate.opsForHash().increment(key,hashKey,-1L);
            return true;
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return false;
        }
    }

    /**
     * 增加哈希中某个属性值
     * @param key
     * @param hashKey
     * @return
     */
    public  boolean increaseHashItemByStep(String key, Object hashKey, Long step){
        try {
            redisTemplate.opsForHash().increment(key,hashKey,step);
            return true;
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return false;
        }
    }

    /**
     * 设置属性值，忽略属性值为null的属性
     * @param key
     * @param obj 设置的属性对象
     * @param time
     * @param timeUnit
     * @return
     */
    public  boolean updateHashAll(String key, Object obj, long time, TimeUnit timeUnit){
        try {
            Map<String, Object> objectMap = objToJsonMap(obj, true);
            redisTemplate.opsForHash().putAll(key,objectMap);
            expire(key,time,timeUnit);
            return true;
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return false;
        }
    }

    public boolean addSet(String key,Object value){
        try {
            String strValue = objToStr(value);
            redisTemplate.opsForSet().add(key,strValue);
            return true;
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return false;
        }
    }

    public boolean addAllSet(String key,Object... values){
        if(values.length == 0){
            return true;
        }
        try {
            String[] strValues = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                strValues[i] = objToStr(values[i]);
            }
            redisTemplate.opsForSet().add(key,strValues);
            return true;
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return false;
        }
    }


    public <T> Set<T> getAndRemoveSet(String key,Class<T> clazz){
        List list = redisTemplate.execute(getAndRemoveListScript(), Collections.singletonList(key));
        if(list == null){
            return Collections.emptySet();
        }
        HashSet<T> res = new HashSet<>();
        for (Object o : list) {
            res.add(JsonUtils.mapperToObject(o,clazz));
        }
        return res;
    }

    public Set<String> getAndRemoveSet(String key){
        List<String> list = redisTemplate.execute(getAndRemoveListScript(), Collections.singletonList(key));
        if(list == null){
            return Collections.emptySet();
        }
        return new HashSet<>(list);
    }

    private DefaultRedisScript<List> getAndRemoveListScript(){
        String script = "local res = redis.call('smembers',KEYS[1])\n redis.call('del',KEYS[1])\n return res";
        return new DefaultRedisScript<>(script, List.class);
    }

    public <T> Set<T> getSet(String key,Class<T> clazz){
        try {
            Set<String> members = redisTemplate.opsForSet().members(key);
            if(members == null){
                return Collections.emptySet();
            }
            HashSet<T> set = new HashSet<>();
            for (String member : members) {
                T value = JsonUtils.toObject(member, clazz);
                set.add(value);
            }
            return set;
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return Collections.emptySet();
        }
    }

    public Boolean incrementZSet(String key,String value,Double score){
        try {
            redisTemplate.opsForZSet().addIfAbsent(key,value,0);
            redisTemplate.opsForZSet().incrementScore(key, value, score);
            return true;
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            return false;
        }
    }

    private static String objToStr(Object object){
        if(object instanceof String){
            return (String)object;
        }
        return JsonUtils.toStr(object);
    }

    /**
     * 将对象转换为键-string 值-string 的map
     * @param obj
     * @param ignoreNullValue
     * @return
     */
    private static Map<String,Object> objToJsonMap(Object obj,boolean ignoreNullValue){
        Map<String, Object> objectMap = BeanUtil.beanToMap(obj, false, ignoreNullValue);
        //手动序列化
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            Object value = entry.getValue();
            String key1 = entry.getKey();
            objectMap.put(key1,objToStr(value));
        }
        return objectMap;
    }
}
