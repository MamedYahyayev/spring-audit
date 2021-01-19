package az.maqa.spring.web.rest;

import az.maqa.spring.domain.User;
import az.maqa.spring.model.dto.UserDTO;
import az.maqa.spring.repository.UserRepository;
import az.maqa.spring.security.AuthoritiesConstants;
import az.maqa.spring.service.UserService;
import az.maqa.spring.web.errors.EmailAlreadyUsedException;
import az.maqa.spring.web.errors.UsernameAlreadyUsedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * RestController for managing users
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final UserService userService;

    private final UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(UserResource.class);

    public UserResource(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * Creates new user if the username and email not used
     *
     * @param userDTO
     * @return ResponseEntity<User> with status 201 (created)
     * @throws BadRequestException if username or email already used
     */
    @PostMapping("/users")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) throws URISyntaxException {
        log.debug("Rest Request to save User {}  ", userDTO);
        if (userRepository.findOneByUsername(userDTO.getUsername().toLowerCase()).isPresent()) {
            throw new UsernameAlreadyUsedException();
        } else if (userRepository.findOneByEmail(userDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyUsedException();
        } else {
            User newUser = userService.createUser(userDTO);
            return ResponseEntity.created(new URI("/api/users/" + newUser.getUsername())).body(newUser);
        }
    }
}
