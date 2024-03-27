package com.web.service.impl;

import com.google.gson.Gson;
import com.web.mapper.UserMapper;
import com.web.model.po.User;
import com.web.service.JwtService;
import com.web.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.gson.io.GsonSerializer;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${token.signing.key}")
    private static final String jwtSigningKey = "33B784E11BA66D1E8350FF7591EF4AE5B4895CC66826ADECF25EEE9B30D059C5";
    @Value("${token.expiration}")
    private final Long tokenExpiration = 60000L;
    @Value("${token.refreshExpiration}")
    private final Long refreshTokenExpiration = 240000L;

    @Resource
    UserMapper userMapper;

    @Resource
    UserService userService;

    @Override
    public String extractUserName(String token) throws AccountExpiredException {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (ExpiredJwtException e){
            Claims claims = e.getClaims();

            User user1 = new User();
            user1.setId(userService.queryByName(claims.get("sub")+"").getId());
            user1.setLock((byte) 0);
            userMapper.updateByPrimaryKeySelective(user1);

            throw new AccountExpiredException("通行证过期了");
        } catch (Exception e) {
            throw new AccountExpiredException("解析不了通行证");
        }
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    @Override
    public String getUserName(String token) {
        return extractAllClaims(token).getSubject();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private static String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {

   /*     return  Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + (60 * 60 + 121) * 1000))
                .addClaims(extraClaims)
                .signWith(getSigningKey() , SignatureAlgorithm.HS256).compact();*/

        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                //.setExpiration(new Date(Instant.now().plusMillis(tokenExpiration).toEpochMilli()))
                .setExpiration(new Date(System.currentTimeMillis() + (60*60*4) * 1000))
                .serializeToJsonWith(new GsonSerializer<>(new Gson()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private static Claims extractAllClaims(String token) {

        return  Jwts.parserBuilder()
                .setSigningKey(getSigningKey()).build()
                .parseClaimsJws(token).getBody();

       /* return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();*/
    }

/*
    public static void main(String[] args) {
        UserVO userVO = new UserVO();
        userVO.setName("a");
        UserDetails userDetails = new CustomUserDetails(userVO);
        System.out.println(generateToken(new HashMap<>(),userDetails));
        System.out.println(extractAllClaims("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwMjExMjM4NSwiZXhwIjoxNzAyMTE2MTA2fQ.rwSFuLtRU7caqCx7LoHfbIDkpTyWhbqKABehmASiHaU"));
    }
*/

    private static Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }



     /*   //过期时间 一天
        private static final long TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000;
        //私钥，随机的uuid
        private static final String TOKEN_SECRET = "ca58f51e-05be-61e8-cbe2-33cebd1e69e8";

        *//**
         * 生成签名，15分钟过期
         * 根据内部改造，支持6中类型，Integer,Long,Boolean,Double,String,Date
         * @param map
         * @return
         *//*
        public static String sign(Map<String,Object> map) {
            try {
                // 设置过期时间
                Date date = new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME);
                // 私钥和加密算法
                Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
                // 设置头部信息
                Map<String, Object> header = new HashMap<>(2);
                header.put("typ", "jwt");
                // 返回token字符串
                JWTCreator.Builder builder =  JWT.create()
                        .withHeader(header)
                        .withIssuedAt(new Date()) //发证时间
                        .withExpiresAt(date);  //过期时间
                //   .sign(algorithm);  //密钥
                // map.entrySet().forEach(entry -> builder.withClaim( entry.getKey(),entry.getValue()));
                map.forEach((key, value) -> {
                    if (value instanceof Integer) {
                        builder.withClaim(key, (Integer) value);
                    } else if (value instanceof Long) {
                        builder.withClaim(key, (Long) value);
                    } else if (value instanceof Boolean) {
                        builder.withClaim(key, (Boolean) value);
                    } else if (value instanceof String) {
                        builder.withClaim(key, String.valueOf(value));
                    } else if (value instanceof Double) {
                        builder.withClaim(key, (Double) value);
                    } else if (value instanceof Date) {
                        builder.withClaim(key, (Date) value);
                    }
                });
                return builder.sign(algorithm);
            } catch (Exception e) {
                log.error(e.getMessage());
                return null;
            }
        }

        *//**
         * 生成签名，15分钟过期
         * 根据内部改造，支持6中类型，Integer,Long,Boolean,Double,String,Date
         * @param o 对象
         * @param key 键
         * @return 秘钥
         *//*
        public static String sign(String key,Object o) {
            try {
                // 设置过期时间
                Date date = new Date(System.currentTimeMillis() + TOKEN_EXPIRE_TIME);
                // 私钥和加密算法
                Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
                // 设置头部信息
                Map<String, Object> header = new HashMap<>(2);
                header.put("typ", "jwt");
                // 返回token字符串
                JWTCreator.Builder builder =  JWT.create()
                        .withHeader(header)
                        .withIssuedAt(new Date()) //发证时间
                        .withExpiresAt(date)  //过期时间
                        .withClaim(key, JSON.toJSONString(o));
                return builder.sign(algorithm);
            } catch (Exception e) {
                log.error(e.getMessage());
                return null;
            }
        }


        *//**
         * 检验token是否正确
         * @param **token**
         * @return
         *//*
        public static boolean verify(String token){
            try {
                Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
                JWTVerifier verifier = JWT.require(algorithm).build();
                verifier.verify(token);

                return true;
            } catch (Exception e){
                log.error(e.getMessage());
                return false;
            }
        }

        *//**
         *获取用户自定义Claim集合
         * @param token
         * @return
         *//*
        public static Map<String, Claim> getClaims(String token){
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            Map<String, Claim> jwt = verifier.verify(token).getClaims();
            return jwt;
        }

        *//**
         *获取用户自定义根据token和字符串拿到String数据
         * @param token
         * @return String
         *//*
        public static String getString(String token,String z) {
            try {
                DecodedJWT jwt = JWT.decode(token);
                return jwt.getClaim(z).asString();
            } catch (JWTDecodeException e) {
                log.error(e.getMessage());
                return null;
            }
        }

        *//**
         *获取用户自定义根据token和字符串拿到对象
         * @param token
         * @return String
         *//*
        public static <T> T getObject(String token, String z, Class<T> tClass) {
            try {
                DecodedJWT jwt = JWT.decode(token);
                String o = jwt.getClaim(z).asString();
                return JSON.parseObject(o,tClass);
            } catch (JWTDecodeException e) {
                log.error(e.getMessage());
                return null;
            }
        }
        *//**
         *获取用户自定义根据token和字符串拿到Integer数据
         * @param token
         * @return Integer
         *//*
        public static Integer getInteger(String token,String z) {
            try {
                DecodedJWT jwt = JWT.decode(token);
                return jwt.getClaim(z).asInt();
            } catch (JWTDecodeException e) {
                log.error(e.getMessage());
                return null;
            }
        }


        *//**
         * 获取过期时间
         * @param token
         * @return
         *//*
        public static Date getExpiresAt(String token){
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            return  JWT.require(algorithm).build().verify(token).getExpiresAt();
        }

        *//**
         * 获取jwt发布时间
         *//*
        public static Date getIssuedAt(String token){
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            return  JWT.require(algorithm).build().verify(token).getIssuedAt();
        }

        *//**
         * 验证token是否失效
         *
         * @param token
         * @return true:过期   false:没过期
         *//*
        public static boolean isExpired(String token) {
            try {
                final Date expiration = getExpiresAt(token);
                return expiration.before(new Date());
            }catch (TokenExpiredException e) {
                log.error(e.getMessage());
                return true;
            }

        }

        *//**
         * 直接Base64解密获取header内容
         * @param token
         * @return
         *//*
        public static String getHeaderByBase64(String token){
            if (StringUtils.isEmpty(token)){
                return null;
            }else {
                byte[] header_byte = Base64.getDecoder().decode(token.split("\\.")[0]);
                String header = new String(header_byte);
                return header;
            }

        }

        *//**
         * 直接Base64解密获取payload内容
         * @param token
         * @return
         *//*
        public static String getPayloadByBase64(String token){

            if (StringUtils.isEmpty(token)){
                return null;
            }else {
                byte[] payload_byte = Base64.getDecoder().decode(token.split("\\.")[1]);
                String payload = new String(payload_byte);
                return payload;
            }

        }

        public static void main(String[] args) throws InterruptedException {
            Map<String,Object> map = new HashMap<>();
            map.put("userId","123456");
            map.put("rose","admin");
            map.put("integer",1111);
            map.put("double",112.222);
            map.put("Long",112L);
            map.put("bool",true);
            map.put("date",new Date());
            String token = sign(map); //生成token
            System.out.println(token);
            System.out.println(verify(token));//验证token是否正确
            String dd = getClaims(token).get("integer").asString(); //使用方法
            System.out.println(dd);
            String userId = getString(token, "userId");  //获取String 类型的 userId
            System.out.println(userId);
            Integer integer = getInteger(token, "integer"); // 获取Integer 类型的 userId
            System.out.println(integer);
            System.out.println("获取签发token时间：" +getIssuedAt(token));
            System.out.println("获取过期时间："+getExpiresAt(token));
            // Thread.sleep(1000*40);
            System.out.println("检查是否已过期："+isExpired(token));
            System.out.println("获取头"+getHeaderByBase64(token));
            System.out.println("获取负荷"+getPayloadByBase64(token));
        }

*/

}