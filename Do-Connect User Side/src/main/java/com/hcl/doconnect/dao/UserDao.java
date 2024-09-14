package com.hcl.doconnect.dao;

import java.util.List;

import com.hcl.doconnect.model.User;

public interface UserDao {

	User findByUsername(String username);

	User findById(Long id);

	void save(User user);

	void delete(User user);

	void update(User user);

	List<User> findAll();

}
