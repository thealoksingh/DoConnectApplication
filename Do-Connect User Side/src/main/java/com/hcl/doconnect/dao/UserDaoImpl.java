package com.hcl.doconnect.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.hcl.doconnect.model.User;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

  
	@Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }


	@Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}
}