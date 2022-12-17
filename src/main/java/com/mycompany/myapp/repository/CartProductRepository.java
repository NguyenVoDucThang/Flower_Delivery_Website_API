package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cart_Product;
import com.mycompany.myapp.domain.composite_key.CartProductKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepository extends JpaRepository<Cart_Product, CartProductKey> {}
