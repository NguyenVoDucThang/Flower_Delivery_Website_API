package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Product;
import com.mycompany.myapp.domain.group.ProductType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Product} entity.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findOneByName(String name);

    Page<Product> findAllByType(Pageable pageable, ProductType type);
}
