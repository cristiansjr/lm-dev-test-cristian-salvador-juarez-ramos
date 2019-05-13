package com.lifebank.utils;

import java.util.Date;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

public class JWT {
	private String secret;
    private Map<String, Object> claims;
    private String subject;
    private String issuer;
    private Long expirationTime;

    public JWT() {
    }

    //For JWT Generation
    /**
     * @param claims elements to be saved in the JWT payload
     * @param secret secret for JWT
     * @param subject short description about information contained in JWT
     * @param issuer entity whom creates the JWT (LifeMiles in this case)
     * @param expirationTime expiration time for JWT
     */
    public JWT(Map<String, Object> claims, String secret, String subject, String issuer, Long expirationTime) {
        this.claims = claims;
        this.secret = secret;
        this.subject = subject;
        this.issuer = issuer;
        this.expirationTime = expirationTime;
    }

    //For JWT Extraction

    /**
     * @param secret secret to decrypt JWT
     */
    public JWT(String secret) {
        this.secret = secret;
    }

    //CYPHERING
    public String generate(){
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder();

        //Setting the specific claims (private claims)
        builder.setClaims(this.claims);

        //Setting the standard claims for the payload
        builder.setSubject(this.subject);
        builder.setIssuer(this.issuer);
        builder.setIssuedAt(now);
        builder.setNotBefore(now);
        builder.setExpiration(new Date(nowMillis + this.expirationTime));

        //Cyphering the JWT
        builder.signWith(SignatureAlgorithm.HS512, this.secret);

        //Generating the JWT
        String token = builder.compact();
        return token;
    }

    //DECIPHERING
    public Claims extract(String jwt) throws ExpiredJwtException, MalformedJwtException, SignatureException{
        //Decrypting the token (Getting the value of the body inside of the token).
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(jwt)
                    .getBody();
            return claims;

        }
        catch (ExpiredJwtException | MalformedJwtException | SignatureException e){
          throw e;
        }
    }
}
