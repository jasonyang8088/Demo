package com.zxxk.zss.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class BaseDAO<T> {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void save(T o) {
		if (o != null) {
			entityManager.persist(o);
		}
	}
	
	public void update(T o) {
		if (o != null) {
			entityManager.merge(o);
		}
	}

}
