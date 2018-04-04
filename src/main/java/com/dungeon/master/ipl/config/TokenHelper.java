package com.dungeon.master.ipl.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import javax.servlet.http.HttpServletRequest;

@Entity
@Service
public class TokenHelper {
    @Autowired
    private HttpServletRequest context;

    static final String SECRET = "Dungeon Master";

    public String getUserNameFromToken() {
        String token = context.getHeader("authorization");
        String username;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;

    }
}
