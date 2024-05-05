package com.gl.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gl.entity.User;
import com.gl.repository.RoleRepository;
import com.gl.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepo;
	private final RoleRepository roleRepo;
	private final PasswordEncoder encoder;
	private static final int ROLE_USER_ID = 2;

	@Override
	public Map<String, String> register(User user) {
		Map<String, String> response = new HashMap<>();
		userRepo.findByEmail(user.getEmail()).ifPresentOrElse(existingUser -> {
			response.put("type", "danger");
			response.put("message", "Student already registered with email " + user.getEmail());
		}, () -> {
			try {
				user.setPassword(encoder.encode(user.getPassword()));
				roleRepo.findById(ROLE_USER_ID).ifPresent(role -> user.setRoles(List.of(role)));
				userRepo.save(user);
				response.put("type", "success");
				response.put("message", "Registration successful!");
			} catch (Exception e) {
				response.put("type", "danger");
				response.put("message", "Registration failed!");
			}
		});
		return response;
	}

	@Override
	public void delete(int id) {
		userRepo.findById(id).ifPresent(user -> {
			user.setRoles(null);
			userRepo.delete(user);
		});
	}

	@Override
	public Page<User> allStudents(Pageable pageable) {
		return userRepo.findAll(pageable);
	}

	@Override
	public long countTotalUsers() {
		return userRepo.count();
	}

	@Override
	public Page<User> searchUsers(String searchTerm, Pageable pageable) {
		return userRepo.findBySearch(searchTerm, pageable);
	}

	@Override
	public User update(User user) {
		return userRepo.findById(user.getId()).map(userFromDb -> {
			BeanUtils.copyProperties(user, userFromDb, "id");
			return userRepo.save(userFromDb);
		}).orElse(null);
	}

	@Override
	public User get(int id) {
		return userRepo.findById(id).orElse(null);
	}
}
