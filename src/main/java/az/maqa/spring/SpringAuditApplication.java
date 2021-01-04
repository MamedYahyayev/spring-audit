package az.maqa.spring;

import az.maqa.spring.domain.Authority;
import az.maqa.spring.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class SpringAuditApplication {

    @Autowired
    private AuthorityRepository repository;

    @PostConstruct
    public void init(){
        repository.save(new Authority("ROLE_USER"));
        repository.save(new Authority("ROLE_ADMIN"));
        repository.save(new Authority("ROLE_ANONYMOUS"));
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringAuditApplication.class, args);
    }

}
