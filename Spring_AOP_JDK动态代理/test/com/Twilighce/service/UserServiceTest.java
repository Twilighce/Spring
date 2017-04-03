package com.Twilighce.service;
import java.lang.reflect.Proxy; 

import org.junit.Test;

import com.Twilighce.aop.LogInterceptor;
import com.Twilighce.dao.UserDAO;
import com.Twilighce.dao.impl.UserDAOImpl;
import com.Twilighce.model.User;
import com.Twilighce.spring.BeanFactory;
import com.Twilighce.spring.ClassPathXmlApplicationContext;


public class UserServiceTest {

	@Test
	public void testAdd() throws Exception {
		BeanFactory applicationContext = new ClassPathXmlApplicationContext();
		  
		
		UserService service = (UserService)applicationContext.getBean("userService");
			
		
		User u = new User();
		u.setUsername("zhangsan");
		u.setPassword("zhangsan");
		service.add(u);
	}
	
	
	@Test
	public void testProxy() {
		
		// Ŀ��ҵ����
		UserDAO userDAO = new UserDAOImpl();
		
		// ���д���
		LogInterceptor li = new LogInterceptor();
		
		// ��Ŀ��ҵ����ͺ��д����֯��һ��
		li.setTarget(userDAO);
		
		// ����ҵ������ࡣ
		// ���� Proxy �ľ�̬����  newProxyInstance��
		// ��һ��������������������ĸ� ClassLoader ����������������Ҫ�ͱ����������ͬһ�� C lassLoader
		// �ڶ��������������Ĵ���ʵ����Ҫʵ�ֵ�һЩ�ӿ�
		// ��������������������֮�󣬵��ô�������ķ���ʱ������һ�� handler ���� 
		UserDAO userDAOProxy = (UserDAO)Proxy.newProxyInstance(userDAO.getClass().getClassLoader(), userDAO.getClass().getInterfaces(), li);
		System.out.println(userDAOProxy.getClass());
		// �������������ʵ����ʵ����Ŀ��ҵ��������нӿ�
		
		// ��������ʵ��
		userDAOProxy.delete();
		userDAOProxy.save(new User());
		
	}
	 
	/*class $Proxy4 implements UserDAO 
	 * {
	 * 	save(User u) {
	 * 	Method m = UserDAO.getclass.getmethod 
	 * li.invoke(this, m, u)
	 * }
	 * }
	 */

}
