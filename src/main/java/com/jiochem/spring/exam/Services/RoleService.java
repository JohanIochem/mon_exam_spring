package com.jiochem.spring.exam.Services;


import com.jiochem.spring.exam.Models.Role;
import com.jiochem.spring.exam.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    RoleRepository roleRepository;

    public Role findByName(String roleName){

        return this.roleRepository.findByRoleName(roleName);
    }

    public void saveRole(Role roleName){

        this.roleRepository.save(roleName);
    }


}
