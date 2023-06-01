package com.stannard.liam.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    Optional<User> findUserByEmail(String email );
    Optional<User> findByUsername(String username);
    void deleteById(Long id);
}
