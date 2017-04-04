package com.Twilighce.dao.impl;

import org.springframework.stereotype.Component;


import com.Twilighce.dao.UserDAO;
import com.Twilighce.model.User;

@Component("u") 
public class UserDAOImpl implements UserDAO {

	public void save(User user) {
		//Hibernate
		//JDBC
		//XML
		//NetWork
		System.out.println("user saved!");
		//throw new RuntimeException("exeption!");
	}

}
