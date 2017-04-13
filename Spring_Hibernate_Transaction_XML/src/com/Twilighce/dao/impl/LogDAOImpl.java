package com.Twilighce.dao.impl;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import com.bjsxt.dao.LogDAO;
import com.bjsxt.model.Log;

@Component("logDAO") 
public class LogDAOImpl implements LogDAO {

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(Log log) {
		
		Session s = sessionFactory.getCurrentSession();
		s.save(log);
		//throw new RuntimeException("error!");
	}

}
