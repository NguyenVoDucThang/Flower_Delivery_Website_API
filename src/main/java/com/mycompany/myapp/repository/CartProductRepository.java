package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Cart_Product;
import com.mycompany.myapp.domain.composite_key.CartProductKey;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepository extends R2dbcRepository<Cart_Product, CartProductKey> {}
