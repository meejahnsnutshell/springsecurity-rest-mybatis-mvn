package com.springrest.repository;

import com.springrest.model.dbForJWT.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {

    // Hibernate automates the following SQL query:
    Role findByRole(String role);

}