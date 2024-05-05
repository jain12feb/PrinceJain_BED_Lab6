package com.gl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.gl.entity.Role;
import com.gl.entity.User;
import com.gl.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	private static final int PAGE_SIZE_DEFAULT = 10;

	@GetMapping("/")
	public String landingPage() {
		return "landingpage";
	}

	@GetMapping("/home")
	public String getAllStudents(Model model, @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "" + PAGE_SIZE_DEFAULT) int pageSize,
			@RequestParam(name = "search", required = false, defaultValue = "") String search) {

		Pageable pageable = PageRequest.of(pageNo, pageSize);

		Page<User> usersPage = search.isEmpty() ? userService.allStudents(pageable)
				: userService.searchUsers(search, pageable);

		model.addAttribute("students", usersPage.getContent());
		model.addAttribute("totalPages", usersPage.getTotalPages());
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("search", search);
		model.addAttribute("totalUsers", usersPage.getTotalElements());
		model.addAttribute("pageSize", PAGE_SIZE_DEFAULT);

		User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Role> loggedInUserRoles = loggedInUser.getRoles();
		List<String> roles = loggedInUserRoles.stream().map(role -> role.getName()).toList();
		model.addAttribute("roles", roles);
		model.addAttribute("fullname", loggedInUser.getFName() + " " + loggedInUser.getLName());

		return "all-students";
	}

	@GetMapping("/add-student-form")
	public String addStudentForm(Model model) {
		model.addAttribute("student", new User());
		return "add-student-form";
	}

	@PostMapping("/add-student")
	public String addStudent(@ModelAttribute("user") User user) {
		userService.register(user);
		return "redirect:/home";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") int id) {
		userService.delete(id);
		return "redirect:/home";
	}

	@GetMapping("/update/{id}")
	public String updatePage(Model model, @PathVariable("id") Integer id) {
		User studentFromDb = userService.get(id);
		if (studentFromDb != null) {
			model.addAttribute("studentFromDb", studentFromDb);
			return "update-form";
		}
		return "redirect:/home";
	}

	@PostMapping("/update-student")
	public String updateTicket(@ModelAttribute("studentFromDb") User user) {
		userService.update(user);
		return "redirect:/home";
	}
}
