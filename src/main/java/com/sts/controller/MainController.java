package com.sts.controller;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sts.dao.userReposetry;
import com.sts.entity.entity;

import jakarta.servlet.http.HttpSession;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController

public class MainController {
	
	@Autowired
	private userReposetry userReposetry;


	@GetMapping("/index")
	public ResponseEntity<List<entity>> getAllUsers() {
		List<entity> users = new ArrayList<>();
		userReposetry.findAll().forEach(users::add);
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	
	@PostMapping("/submit")
	public String submit(@ModelAttribute entity user, HttpSession session) {
		userReposetry.save(user);
		session.setAttribute("msg", "Registration Successfully");
		return "/index.html";
	}


//	edit user by id
	@PostMapping("/user/{id}")
	public String editUser(@PathVariable Integer id, Model model) {
		Optional<entity> user = userReposetry.findById(id);
		entity user1 = user.orElse(new entity());
		model.addAttribute("user", user1);
		return "edit";
	}
	
	@PostMapping("/delete/{id}")
	public String deleteUser(@PathVariable Integer id) {
		userReposetry.deleteById(id);
		return "redirect:/index";
	}


//update user by id
	@PostMapping("/update")
	public String updateUser(@ModelAttribute entity user, HttpSession session) {
		userReposetry.save(user);
		session.setAttribute("msg", "Updated Successfully");
		return "redirect:/index";
	}
  
}