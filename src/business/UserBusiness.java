package business;

import utils.Cryptography.BCrypt;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class UserBusiness {
    private String SECRET_KEY = "secret_key";
    public String generateToken(String email) {
        try {
            // String jwt = Jwts.builder()
            //     .setSubject(email)
            //     .setIssuedAt(new Date())
            //     .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // 1 hour expiration
            //     .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            //     .compact();

            return "xxxxxxx";
        } catch (Exception ex) {
            throw ex;
        }
    }

    public Boolean isTokenValid(String token) {
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

            String email = claims.getSubject();
            Date expiration = claims.getExpiration();
            Date now = new Date();

            return email == null || expiration == null || now.after(expiration) ? false : true;
        } catch (Exception ex) {
            return false;
        }
    }


    public String encryptPassword(String password){
     
        String encryptPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        return encryptPassword;
    }

    public boolean checkPassword(String inputPassword, String encryptedPassword) {
        return BCrypt.checkpw(inputPassword, encryptedPassword);
    }
}
