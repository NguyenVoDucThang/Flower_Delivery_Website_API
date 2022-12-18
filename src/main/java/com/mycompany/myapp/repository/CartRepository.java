package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cart;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.group.CartStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link Cart} entity.
 */
@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    Optional<Cart> findOneById(String id);

    Page<Cart> findAllByStatus(Pageable pageable, CartStatus cartStatus);

    Page<Cart> findAllByStatusAndUser(Pageable pageable, CartStatus cartStatus, User user);
}
