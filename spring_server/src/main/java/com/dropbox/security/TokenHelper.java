package com.dropbox.security;

import com.dropbox.model.Users;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
//import java.security.Key;

@Component
public class TokenHelper {
    @Value("${jwt.secret}")
    public String SECRET;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String generateToken(Users user) {
        String customSubject = String.format("{ \"firstname\": \"%s\",\"lastname\": \"%s\",\"email\": \"%s\",\"_id\": \"%s\" }", user.firstname, user.lastname, user.email, user._id );
        String token = Jwts.builder()
                .setSubject(customSubject)
                .setId(user._id)
                .signWith( SIGNATURE_ALGORITHM, SECRET )
                .compact();


        String decoded = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
        System.out.println("jwt decode ------------");
        System.out.println(decoded);

        return token;
    }

}
