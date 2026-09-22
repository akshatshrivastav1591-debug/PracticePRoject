package com.UpgradingSkillsDemo.Test.Security.Jwt;
import com.UpgradingSkillsDemo.Test.GlobalExceptionHandeller.CustomException.AccessTokenExceptions;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Service
public class JwtFilterServiceClass {
    @Value("${jwt.secret}")
    private String secretKey;


    public  String getAccessToken(String email,String role){
        Map<String,Object> claims=new HashMap<>();
        claims.put("role",role);

        return Jwts.builder()
                .claims()
                .empty()
                .add(claims)
                .and()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*5))
                .signWith(getKey())
                .compact();
    }
    public  String getRefreshToken(int refreshTokenId){
        Map<String,Integer> claims=new HashMap<>();
        claims.put("refreshTokenId",refreshTokenId);
        return Jwts.builder()
                .claims()
                .empty()
                .add(claims)
                .and()
                .issuedAt(new Date())
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey(){
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private  <T>T extractClaims(String token, Function<Claims,T>claimResolver){
        final  Claims claims=extractAllClaims(token);
        return  claimResolver.apply(claims);

    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }catch (ExpiredJwtException e){
            throw  AccessTokenExceptions.expired();
        }catch (JwtException e){
            throw AccessTokenExceptions.invalid();
        }
    }
    public String extractEmail(String token) {

        return extractClaims(token,Claims::getSubject);
    }
    public String extractRole(String token) {
        return extractClaims(token, claims -> claims.get("role", String.class));
    }

    public boolean validateToken(String token)  {
        if  (isTokenExpired(token)) throw AccessTokenExceptions.expired();
        return  true;
    }

    public Integer extractRefreshTokenId(String token) {
        return extractClaims(token, claims -> claims.get("refreshTokenId", Integer.class));
    }

    private boolean isTokenExpired(String token) {
        return  extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return  extractClaims(token,Claims::getExpiration);
    }

    public String extractRefreshTokenFromCookies(HttpServletRequest request) {
        if (request.getCookies() == null) return null;
        for (Cookie cookie : request.getCookies()) {
            if ("jwt".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
