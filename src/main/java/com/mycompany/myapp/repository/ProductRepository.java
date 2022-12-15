package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.group.ProductType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the {@link Product} entity.
 */
@Repository
public interface ProductRepository extends R2dbcRepository<Product, String> {
    Mono<Product> findOneByName(String name);

    Flux<Product> findAllByType(Pageable pageable, ProductType type);

    Mono<Long> countByType(ProductType type);
}
