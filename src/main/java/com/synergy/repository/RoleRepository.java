package com.synergy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synergy.domain.Role;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	public Role findByRoleNameAllIgnoreCase(String role);

}
