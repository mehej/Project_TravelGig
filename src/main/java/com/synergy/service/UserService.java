package com.synergy.service;

import java.util.List;

import com.synergy.domain.User;

public interface UserService {
	public List<User> findAll();
	public User save(User u);
	public void deleteUserById(long uId);
	public User findByUserId(long uId);
	public User findByUserName(String userName);
}
