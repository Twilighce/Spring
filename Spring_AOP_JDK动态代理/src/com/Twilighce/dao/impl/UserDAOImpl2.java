package com.Twilighce.dao.impl;

import com.Twilighce.model.User;

public class UserDAOImpl2 extends UserDAOImpl {
	@Override
	public void save(User user) {
		
		System.out.println("save start...");
		super.save(user);
		
	}
}
