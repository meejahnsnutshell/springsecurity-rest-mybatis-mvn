package com.springrest.repository;

import com.springrest.model.dbForJWT.Userz;
import com.springrest.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserzRepository extends JpaRepository<Userz, Long> {

    // Hibernate automates the following SQL query:
    Userz findByUsername(String username);
}