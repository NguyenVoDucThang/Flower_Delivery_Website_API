package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Delivery;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends R2dbcRepository<Delivery, String> {}
