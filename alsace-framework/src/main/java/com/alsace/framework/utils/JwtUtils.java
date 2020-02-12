package com.alsace.framework.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;

/**
 * JWT相关工具类
 */
@Slf4j
public class JwtUtils {

  /**
   * 校验token是否正确
   *
   * @param token 密钥
   * @param secret 用户的密码
   * @return 是否正确
   */
  public static boolean verify(String token, String loginAccount, String secret) {
    try {
      //根据密码生成JWT效验器
      Algorithm algorithm = Algorithm.HMAC256(secret);
      JWTVerifier verifier = JWT.require(algorithm)
          .withClaim("loginAccount", loginAccount)
          .build();
      //效验TOKEN
      DecodedJWT jwt = verifier.verify(token);
      return true;
    } catch (Exception exception) {
      LogUtils.printError(log, exception);
      return false;
    }
  }

  /**
   * 获得token中的信息无需secret解密也能获得
   *
   * @return token中包含的用户名
   */
  public static String getLoginAccount(String token) {
    try {
      DecodedJWT jwt = JWT.decode(token);
      return jwt.getClaim("loginAccount").asString();
    } catch (JWTDecodeException e) {
      LogUtils.printError(log, e);
      return null;
    }
  }

  /**
   * 生成签名
   *
   * @param loginAccount 用户名
   * @param secret 用户的密码
   * @param secret 用户的密码
   * @return 加密的token
   */
  public static String sign(String loginAccount, String secret, int expireMinute) {
    Date date = new Date(System.currentTimeMillis() + expireMinute * 60 * 1000);
    Algorithm algorithm = Algorithm.HMAC256(secret);
    // 附带loginAccount信息
    return JWT.create()
        .withClaim("loginAccount", loginAccount)
        .withExpiresAt(date)
        .sign(algorithm);

  }

}
