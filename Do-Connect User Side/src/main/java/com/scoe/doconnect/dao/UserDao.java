package com.scoe.doconnect.dao;

import java.util.List;

import com.scoe.doconnect.model.User;

public interface UserDao {

	User findByUsername(String username);

	User findById(Long id);

	void save(User user);

	void delete(User user);

	void update(User user);

	List<User> findAll();

}
