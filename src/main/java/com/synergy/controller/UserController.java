package com.synergy.controller;

import java.security.Principal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.synergy.domain.User;
import com.synergy.domain.Role;
import com.synergy.repository.RoleRepository;
import com.synergy.repository.UserRepository;
import com.synergy.service.UserServiceImpl;




@Controller
@SessionAttributes("user")
public class UserController {
	
	@Autowired
	UserServiceImpl userserviceimpl;
	
	@Autowired
	RoleRepository rolerepository;
	
	

		
	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;
	
	@RequestMapping(value="/login")
	public String login(@RequestParam(required=false) String logout, @RequestParam(required=false) String error, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Model model) {
		String message = "";
		if(error!=null) {
			message="Invalid Credentials";
		}
		if(logout!=null) {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth!=null) {
				new SecurityContextLogoutHandler().logout(httpServletRequest, httpServletResponse, auth);
			}
			message="Logout";
			return "login";
		}
		model.addAttribute("Message", message);
		return "login";
		
	}
	
	@RequestMapping(value="/accessDeniedPage")
	public String accessDenied(Principal principal, Model model) {
		String message = principal.getName()+", Unauthorised access";
		model.addAttribute("Message", message);
		return "accessDenied";
		
	}
	
	
	@RequestMapping(value="/signup")
	public String signup() {
		return "signup";
	
	}	
	
	@RequestMapping(value="/saveUser" ,method = RequestMethod.POST)
	public String saveUser(@RequestParam String userName,@RequestParam String password,@RequestParam String userEmail,@RequestParam String userPhone) {
		System.out.println("inside user controller saveuser");
		 String passWord=bcryptPasswordEncoder.encode(password);
		 User u= new User();
		 
		 System.out.println("inside user controller 111");
		 Role userRole=rolerepository.findByRoleNameAllIgnoreCase("role_user");	
		 System.out.println("inside user controller saveuser 2222");
		Set<Role> setroles = new HashSet<>();		
		setroles.add(userRole);
		u.setRoles(setroles);		
		 u.setUserPassword(passWord);
		 u.setUserName(userName);
		 u.setUserPhone(userPhone);
		 u.setUserEmail(userEmail);
		 userserviceimpl.save(u); 
		return "login";	
	}	
	
	
	
	
}
