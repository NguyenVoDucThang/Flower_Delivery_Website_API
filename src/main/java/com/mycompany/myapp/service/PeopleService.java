package com.mycompany.myapp.service;

import com.mycompany.myapp.config.Constants;
import com.mycompany.myapp.domain.Authority;
import com.mycompany.myapp.domain.People;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.repository.AuthorityRepository;
import com.mycompany.myapp.repository.PeopleRepository;
import com.mycompany.myapp.security.AuthoritiesConstants;
import com.mycompany.myapp.security.SecurityUtils;
import com.mycompany.myapp.service.dto.AdminUserDTO;
import com.mycompany.myapp.web.rest.vm.PeopleVM;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import tech.jhipster.security.RandomUtil;

@Service
public class PeopleService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final PeopleRepository peopleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthorityRepository authorityRepository;

    public PeopleService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder, AuthorityRepository authorityRepository) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
    }

    @Transactional
    public Mono<People> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return peopleRepository
            .findOneByActivationKey(key)
            .flatMap(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                return saveUser(user);
            })
            .doOnNext(user -> log.debug("Activated user: {}", user));
    }

    @Transactional
    public Mono<People> registerUser(PeopleVM peopleVM) {
        return peopleRepository
            .findOneByEmailIgnoreCase(peopleVM.getEmail())
            .flatMap(existingUser -> {
                if (!existingUser.isActivated()) {
                    return peopleRepository.delete(existingUser);
                } else {
                    return Mono.error(new EmailAlreadyUsedException());
                }
            })
            .publishOn(Schedulers.boundedElastic())
            .then(
                Mono.fromCallable(() -> {
                    People newUser = new People();
                    String encryptedPassword = passwordEncoder.encode(peopleVM.getPassword());
                    // new user gets initially a generated password
                    newUser.setPassword(encryptedPassword);
                    if (peopleVM.getEmail() != null) {
                        newUser.setEmail(peopleVM.getEmail().toLowerCase());
                    }
                    // new user is not active
                    newUser.setActivated(false);
                    // new user gets registration key
                    newUser.setActivationKey(RandomStringUtils.random(6, 0, 0, false, true));
                    return newUser;
                })
            )
            .flatMap(newUser -> {
                Set<Authority> authorities = new HashSet<>();
                return authorityRepository
                    .findById(AuthoritiesConstants.USER)
                    .map(authorities::add)
                    .thenReturn(newUser)
                    .doOnNext(user -> user.setAuthorities(authorities))
                    .flatMap(this::saveUser)
                    .doOnNext(user -> log.debug("Created Information for User: {}", user));
            });
    }

    @Transactional
    public Mono<People> saveUser(People people) {
        return SecurityUtils
            .getCurrentUserLogin()
            .switchIfEmpty(Mono.just(Constants.SYSTEM))
            .flatMap(login -> {
                if (people.getCreatedBy() == null) {
                    people.setCreatedBy(login);
                }
                people.setLastModifiedBy(login);
                // Saving the relationship can be done in an entity callback
                // once https://github.com/spring-projects/spring-data-r2dbc/issues/215 is done
                return peopleRepository
                    .save(people)
                    .flatMap(savedUser ->
                        Flux
                            .fromIterable(people.getAuthorities())
                            .flatMap(authority -> peopleRepository.savePeopleAuthority(savedUser.getId(), authority.getName()))
                            .then(Mono.just(savedUser))
                    );
            });
    }
}
