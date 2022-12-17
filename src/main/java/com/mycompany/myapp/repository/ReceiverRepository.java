package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiverRepository extends JpaRepository<Receiver, String> {}
