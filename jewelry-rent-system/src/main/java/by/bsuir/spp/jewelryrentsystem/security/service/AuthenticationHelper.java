package by.bsuir.spp.jewelryrentsystem.security.service;

import by.bsuir.spp.jewelryrentsystem.security.exception.InvalidTokenAuthenticationException;
import by.bsuir.spp.jewelryrentsystem.security.model.JwtTokenPayload;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.MacSigner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthenticationHelper {
    public static final String AUTHENTICATION_HEADER = "JrsAuthToken";

    private final String SECRET = "W9ERI45RWE46WQG52M9RRUYLCMMN6G";

    private Long tokenExpirationTime = 86400L;

    private final ObjectMapper objectMapper;

    public String generateToken(final Long userId) {
        try {
            JwtTokenPayload payload = new JwtTokenPayload(
                    userId,
                    Instant.now().getEpochSecond() + this.tokenExpirationTime
            );

            String token = this.objectMapper.writeValueAsString(payload);
            return JwtHelper.encode(token, new MacSigner(SECRET)).getEncoded();
        } catch (JsonProcessingException exception) {
            throw new InternalAuthenticationServiceException("Error generating token.", exception);
        }
    }

    public JwtTokenPayload decodeToken(final String token) {
        if (Objects.isNull(token)) {
            throw new InvalidTokenAuthenticationException("Token is null");
        }

        String[] array = token.split(" ");
        Jwt jwt = JwtHelper.decode(array[array.length - 1]);

        try {
            jwt.verifySignature(new MacSigner(SECRET));
        } catch (Exception exception) {
            throw new InvalidTokenAuthenticationException("Token signature invalid", exception);
        }

        String claims = jwt.getClaims();
        JwtTokenPayload tokenPayload;

        try {
            tokenPayload = this.objectMapper.readValue(claims, JwtTokenPayload.class);
        } catch (IOException exception) {
            throw new InvalidTokenAuthenticationException("Unable to extract data from token", exception);
        }

        return tokenPayload;
    }
}
