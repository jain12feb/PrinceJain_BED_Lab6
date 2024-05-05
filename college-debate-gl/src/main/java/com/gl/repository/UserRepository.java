package com.gl.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gl.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	@Query("SELECT u FROM User u WHERE LOWER(u.fName) LIKE %:search% OR LOWER(u.lName) LIKE %:search% OR LOWER(u.email) LIKE %:search%")
	Page<User> findBySearch(String search, Pageable pageable);

}
