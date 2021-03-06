package az.maqa.spring.web.rest;

import az.maqa.spring.model.request.RequestLogin;
import az.maqa.spring.model.response.ResponseToken;
import az.maqa.spring.security.jwt.JWTFilter;
import az.maqa.spring.security.jwt.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authenticating user
 */
@RestController
@RequestMapping("/api")
public class JWTController {


    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public JWTController(TokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Authenticate user with Spring Security and give the token
     *
     * @param login model (holds username and password)
     * @return token in body
     */
    @PostMapping("/authenticate")
    public ResponseEntity<ResponseToken> authenticate(@RequestBody RequestLogin login) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());

        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new ResponseToken(jwt), httpHeaders, HttpStatus.OK);
    }

}