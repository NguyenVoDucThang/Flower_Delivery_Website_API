package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cart;
import com.mycompany.myapp.domain.group.CartStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the {@link Cart} entity.
 */
@Repository
public interface CartRepository extends R2dbcRepository<Cart, String> {
    Mono<Cart> findOneById(String id);

    Flux<Cart> findAllByStatus(Pageable pageable, CartStatus cartStatus);

    Mono<Long> countByStatus(CartStatus cartStatus);
}
