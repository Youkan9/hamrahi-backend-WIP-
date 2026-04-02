    package com.bxb.hamrahi_app.jwt;

    import com.bxb.hamrahi_app.model.User;
    import io.jsonwebtoken.Claims;
    import io.jsonwebtoken.Jwts;
    import io.jsonwebtoken.io.Decoders;
    import io.jsonwebtoken.security.Keys;
    import jakarta.annotation.PostConstruct;
    import lombok.RequiredArgsConstructor;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Component;
    import javax.crypto.SecretKey;
    import java.util.Date;

    /**
     * Utility class for handling JWT operations such as token generation,
     * validation, and extraction of claims.
     */
    @Component
    @RequiredArgsConstructor
    public class JwtUtil {

        /** Secret key used for signing JWT tokens,
         *  injected from application properties. */
        @Value("${jwt.secret}")
        private String secret;

        /** Expiration time for access tokens,
         * injected from application properties. */
        @Value("${jwt.access-token-expiration}")
        private long accessTokenExpiration;

        /** Expiration time for refresh tokens,
         * injected from application properties. */
        @Value("${jwt.refresh-token-expiration}")
        private long refreshTokenExpiration;

        /** Secret key used for signing JWT tokens, initialized lazily. */
        private SecretKey signingKey;

        /** Retrieves the signing key for JWT operations. */
        @PostConstruct
        public void init() {
            byte[] keyBytes = Decoders.BASE64URL.decode(secret);
            this.signingKey = Keys.hmacShaKeyFor(keyBytes);
        }

        /** Generates an access token for the given phone number. */
        public String generateAccessToken(User user){
            return Jwts.builder()
                    .subject(String.valueOf(user.getUserId()))
                    .claim("type", "ACCESS")
                    .claim("role", user.getUserRole())
                    .issuedAt(new Date())
                    .expiration(
                new Date(System.currentTimeMillis() + accessTokenExpiration))
                    .signWith(signingKey, Jwts.SIG.HS256)
                    .compact();
        }

        /** Generates a refresh token for the given phone number. */
        public String generateRefreshToken(User user){
            return Jwts.builder()
                    .subject(String.valueOf(user.getUserId()))
                    .claim("type", "REFRESH")
                    .claim("role", user.getUserRole())
                    .issuedAt(new Date())
                    .expiration(
                     new Date(
                System.currentTimeMillis() + refreshTokenExpiration))
                    .signWith(signingKey, Jwts.SIG.HS256)
                    .compact();
        }

        /** Extracts the phone number (subject) from the given JWT token. */
        public String extractUserId(String token){
            return extractClaims(token).getSubject();
        }

        /** Validates the given JWT token against the provided phone number. */
        public boolean isTokenValid(String token, String userId){
            final String extractedPhone = extractUserId(token);
            return extractedPhone.equals(userId) && !isTokenExpired(token);
        }

        /** Checks if the given JWT token has expired. */
        public boolean isTokenExpired(String token){
            return extractClaims(token)
                    .getExpiration()
                    .before(new Date());
        }

        /** Extracts claims from the given JWT token. */
        private Claims extractClaims(String token){
            return Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        }

        /** Extracts the token type (ACCESS or REFRESH)
         *  from the given JWT token. */
        public String extractTokenType(String token){
            return extractClaims(token).get("type", String.class);
        }

        public String extractUserRole(String token){
            return extractClaims(token).get("role",String.class);
        }
    }