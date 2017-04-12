package com.Twilighce.dao.impl;

import com.Twilighce.aop.LogInterceptor;
import com.Twilighce.dao.UserDAO;
import com.Twilighce.model.User;

public class UserDAOImpl3 implements UserDAO {
	
	private UserDAO userDAO = new UserDAOImpl();
	
	public void save(User user) {
		
		new LogInterceptor().beforeMethod(null);
		userDAO.save(user);
		
		
	}

	public void delete() {
		// TODO Auto-generated method stub
		
	}
}
