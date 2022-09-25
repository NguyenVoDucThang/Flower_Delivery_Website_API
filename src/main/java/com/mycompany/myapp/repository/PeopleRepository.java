package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.People;
import com.mycompany.myapp.domain.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PeopleRepository extends R2dbcRepository<People, Long> {
    Mono<People> findOneByActivationKey(String activationKey);

    Mono<People> findOneByEmailIgnoreCase(String email);

    @Query("INSERT INTO jhi_people_authority VALUES(:peopleId, :authority)")
    Mono<Void> savePeopleAuthority(Long peopleId, String authority);
}
