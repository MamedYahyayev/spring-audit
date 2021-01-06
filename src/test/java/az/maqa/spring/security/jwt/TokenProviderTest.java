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

    TokenProvider tokenProvider;
    Key key;

    @BeforeEach
    void setUp() {
        tokenProvider = new TokenProvider();

        key = Keys.hmacShaKeyFor(Decoders.BASE64
                .decode("dasfdijwdoiasofdhasufhsaoufhdoasuhdasfdijwdoiasofdhasufhsaoufoadasfdijwdoiasofdhasufhsaous"));

        ReflectionTestUtils.setField(tokenProvider, "key", key);
    }

    @Test
    void createTokenTest() {
        Authentication authentication = crateAuthentication();
        String token = tokenProvider.createToken(authentication);

        assertThat(token).isNotEmpty();
    }

    private Authentication crateAuthentication() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
        return new UsernamePasswordAuthenticationToken("anonymous", "anonymous", authorities);
    }


}
