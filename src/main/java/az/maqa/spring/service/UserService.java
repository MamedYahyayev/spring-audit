package az.maqa.spring.service;

import az.maqa.spring.model.dto.UserDTO;
import az.maqa.spring.domain.Authority;
import az.maqa.spring.domain.User;
import az.maqa.spring.repository.AuthorityRepository;
import az.maqa.spring.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Layer for managing user
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final AuthorityRepository authorityRepository;
    private final UserRepository userRepository;

    public UserService(AuthorityRepository authorityRepository, UserRepository userRepository) {
        this.authorityRepository = authorityRepository;
        this.userRepository = userRepository;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setActivated(true);

        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO.getAuthorities().stream()
                    .map(authority -> authorityRepository.findById(authority.getName()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());

            user.setAuthorities(authorities);
        }

        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }
}
