package com.example.taxis.repository;

import com.example.taxis.entity.Client;
import org.springframework.data.jpa.repository.*;
public interface ClientRepository extends JpaRepository<Client,Long>, JpaSpecificationExecutor<Client> {}
