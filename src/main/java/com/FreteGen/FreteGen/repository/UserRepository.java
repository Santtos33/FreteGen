package com.FreteGen.FreteGen.repository;


import com.FreteGen.FreteGen.user.Clients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Clients,String> {
     UserDetails findByLogin(String login);

}
