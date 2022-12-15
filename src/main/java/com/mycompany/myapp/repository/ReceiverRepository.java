package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Receiver;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiverRepository extends R2dbcRepository<Receiver, String> {}
