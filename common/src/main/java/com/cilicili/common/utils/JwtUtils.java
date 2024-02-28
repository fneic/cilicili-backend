package com.cilicili.common.utils;

import cn.hutool.crypto.digest.DigestUtil;
import com.cilicili.common.model.SafeUser;
import com.cilicili.common.exception.BusinessException;
import com.cilicili.common.exception.ThrowUtils;
import com.cilicili.common.resp.StatusCode;
import com.cilicili.common.utils.JsonUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.SecretKey;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @ClassName JWTUtils
 * @Description jwt 工具
 * @Author Zhou JunJie
 * @Date 2023/11/15 13:58
 **/
@Slf4j
public class JwtUtils {

    private JwtUtils(){}

    private static final SecretKey KEY;
    private static final String APP_SECRET = "6CFitx-ouNjYWT-7mA6xBc";

    private static final String LOG_TEMPLATE = "JWT Operation Failed:{}\n{}";

    private static final int ACCESS_TOKEN_EXPIRE = 20;//分钟
    private static final int REFRESH_TOKEN_EXPIRE = 15;//day

    private static final String ACCESS_SUBJECT = "access_token";
    private static final String REFRESH_SUBJECT = "refresh_token";

    static {
        byte[] bytes = DigestUtil.sha256(APP_SECRET);
        KEY = Keys.hmacShaKeyFor(bytes);
    }

    public static String generateAccessToken(SafeUser safeUser) {
        try {
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.MINUTE, ACCESS_TOKEN_EXPIRE);
            return Jwts.builder().subject(ACCESS_SUBJECT).issuedAt(new Date()).claim("user", safeUser).expiration(instance.getTime()).signWith(KEY).compact();
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            throw e;
        }
    }

    public static String generateRefreshToken(SafeUser safeUser) {
        try {
            Calendar instance = Calendar.getInstance();
            //refresh_token 设置半个月有效期
            instance.add(Calendar.DAY_OF_MONTH, REFRESH_TOKEN_EXPIRE);
            return Jwts.builder().subject(REFRESH_SUBJECT).issuedAt(new Date()).claim("uid", safeUser.getId()).expiration(instance.getTime()).signWith(KEY).compact();
        }catch (Exception e){
            log.error(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            throw e;
        }
    }

    public static SafeUser getUserByAccessToken(String token) {
//        return getUser(token,"access_token");
        return getUser(token);

    }


    private static SafeUser getUser(String accessToken){
        try {
            ThrowUtils.throwIf(StringUtils.isEmpty(accessToken), StatusCode.NOT_LOGIN_ERROR);
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(KEY).build().parseSignedClaims(accessToken);
            if (!Objects.equals(claimsJws.getPayload().getSubject(),ACCESS_SUBJECT)) {
                throw new BusinessException(StatusCode.OPERATION_ERROR);
            }
            Object userMapper = claimsJws.getPayload().get("user");
            return JsonUtils.mapperToObject(userMapper, SafeUser.class);
        }catch (SignatureException | MalformedJwtException e){
            log.info(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            ThrowUtils.throwException(StatusCode.NOT_LOGIN_ERROR);
        }catch (ExpiredJwtException e){
            log.info(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            ThrowUtils.throwException(StatusCode.ACCESS_TOKEN_EXPIRE);
        }catch (BusinessException e){
            log.info(LOG_TEMPLATE,ACCESS_SUBJECT + "校验失败 " + e.getMessage(),e.getStackTrace());
            throw e;
        } catch (IllegalArgumentException e) {
            throw new UnsupportedOperationException(e);
        }
        return null;
    }

    private static SafeUser getUser(String token,String subject){
        try {
            ThrowUtils.throwIf(StringUtils.isEmpty(token), StatusCode.NOT_LOGIN_ERROR);
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(KEY).build().parseSignedClaims(token);
            if (!subject.equals(claimsJws.getPayload().getSubject())) {
                throw new BusinessException(StatusCode.OPERATION_ERROR);
            }
            Object userMapper = claimsJws.getPayload().get("user");
            return JsonUtils.mapperToObject(userMapper, SafeUser.class);
        }catch (SignatureException | MalformedJwtException e){
            log.info(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            ThrowUtils.throwException(StatusCode.NOT_LOGIN_ERROR);
        }catch (ExpiredJwtException e){
            log.info(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            ThrowUtils.throwException(StatusCode.ACCESS_TOKEN_EXPIRE);
        }catch (BusinessException e){
            log.info(LOG_TEMPLATE,subject + "校验失败 " + e.getMessage(),e.getStackTrace());
            throw e;
        } catch (IllegalArgumentException e) {
            throw new UnsupportedOperationException(e);
        }
        return null;
    }

    public static Long getUidByRefreshToken(String refreshToken){
        try {
            Claims payload = Jwts.parser().verifyWith(KEY).build().parseSignedClaims(refreshToken).getPayload();
            if (!"refresh_token".equals(payload.getSubject())) {
                throw new BusinessException(StatusCode.OPERATION_FAILED);
            }
            Integer uid = (Integer) payload.get("uid");
            return uid.longValue();
        }catch (SignatureException | ExpiredJwtException | MalformedJwtException e){
            log.info(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            ThrowUtils.throwException(StatusCode.NOT_LOGIN_ERROR);
        }catch (BusinessException e){
            log.info(LOG_TEMPLATE,"refresh_token校验失败 " + e.getMessage(),e.getStackTrace());
            throw e;
        } catch (IllegalArgumentException e) {
            throw new UnsupportedOperationException(e);
        }
        return null;
    }
    @Deprecated
    public static String refresh(String refreshToken){
        try {
            Claims payload = Jwts.parser().verifyWith(KEY).build().parseSignedClaims(refreshToken).getPayload();
            if (!"refresh_token".equals(payload.getSubject())) {
                throw new BusinessException(StatusCode.OPERATION_FAILED);
            }
            Object userMapper = payload.get("user");
            SafeUser userInfo = JsonUtils.mapperToObject(userMapper, SafeUser.class);
            return generateAccessToken(userInfo);
        }catch (SignatureException | ExpiredJwtException | MalformedJwtException e){
            log.info(LOG_TEMPLATE,e.getMessage(),e.getStackTrace());
            ThrowUtils.throwException(StatusCode.NOT_LOGIN_ERROR);
        }catch (BusinessException e){
            log.info(LOG_TEMPLATE,"refresh_token校验失败 " + e.getMessage(),e.getStackTrace());
            throw e;
        } catch (IllegalArgumentException e) {
            throw new UnsupportedOperationException(e);
        }
        return null;
    }
}
