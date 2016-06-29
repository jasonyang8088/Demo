package com.zxxk.zss.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class BaseDAO<T> {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public Serializable save(T o) {
		if (o != null) {
			entityManager.persist(o);
		}
		return null;
	}

}
