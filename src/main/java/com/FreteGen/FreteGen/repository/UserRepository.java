package com.FreteGen.FreteGen.repository;


import com.FreteGen.FreteGen.user.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Clients, UUID> {
     UserDetails findByLogin(String login);

}
