package com.gl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
