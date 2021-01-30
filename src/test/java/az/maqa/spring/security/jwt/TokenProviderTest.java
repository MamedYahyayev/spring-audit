package az.maqa.spring.security.jwt;

import az.maqa.spring.security.AuthoritiesConstants;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class TokenProviderTest {

    public static final int ONE_MINUTE = 60000;
    private TokenProvider tokenProvider;
    private Key key;

    @BeforeEach
    void setUp() {
        tokenProvider = new TokenProvider();
        key = Keys.hmacShaKeyFor(Decoders.BASE64
                .decode("fd54a45s65fds737b9aafcb3412e07ed99b267f33413274720ddbb7f6c5e64e9f14075f2d7ed041592f0b7657baf8"));

        ReflectionTestUtils.setField(tokenProvider, "key", key);
        ReflectionTestUtils.setField(tokenProvider, "expiration", -ONE_MINUTE);
    }

    @Test
    void testReturnTrueIfTokenIsNotEmpty() {
        Authentication authentication = createAuthentication();
        String token = tokenProvider.createToken(authentication);
        assertThat(token).isNotEmpty();
    }

    @Test
    void testReturnFalseWhenTokenExpired() {
        Authentication authentication = createAuthentication();
        String token = tokenProvider.createToken(authentication);

        boolean isValid = tokenProvider.validateToken(token);

        assertThat(isValid).isEqualTo(false);
    }

    private Authentication createAuthentication() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
        return new UsernamePasswordAuthenticationToken("anonymous", "anonymous", authorities);
    }


}
