package com.jiochem.spring.exam.Repository;

import com.jiochem.spring.exam.Models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByRoleName(String roleName);
}