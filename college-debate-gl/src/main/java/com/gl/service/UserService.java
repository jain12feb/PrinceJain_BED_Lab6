package com.gl.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.gl.entity.User;

public interface UserService {

	Map<String, String> register(User user);

	User get(int id);

	void delete(int id);

	Page<User> allStudents(Pageable pageable);

	long countTotalUsers();

	Page<User> searchUsers(String searchTerm, Pageable pageable);

	User update(User user);

}
