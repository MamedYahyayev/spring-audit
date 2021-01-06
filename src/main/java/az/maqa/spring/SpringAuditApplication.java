package az.maqa.spring;

import az.maqa.spring.domain.Authority;
import az.maqa.spring.repository.AuthorityRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SpringAuditApplication {

    private final AuthorityRepository repository;

    public SpringAuditApplication(AuthorityRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringAuditApplication.class, args);
    }

    @PostConstruct
    public void init() {
        List<Authority> authorities = Arrays.asList(new Authority("ROLE_USER"),
                new Authority("ROLE_ADMIN"),
                new Authority("ROLE_ANONYMOUS"));

        repository.saveAll(authorities);
    }

}
