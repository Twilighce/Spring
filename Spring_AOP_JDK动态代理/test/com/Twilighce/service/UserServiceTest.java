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
		
		// 目标业务类
		UserDAO userDAO = new UserDAOImpl();
		
		// 横切代码
		LogInterceptor li = new LogInterceptor();
		
		// 将目标业务类和横切代码编织到一起
		li.setTarget(userDAO);
		
		// 创建业务代理类。
		// 调用 Proxy 的静态方法  newProxyInstance。
		// 第一个参数：类加载器。用哪个 ClassLoader 来产生这个代理对象，要和被代理对象用同一个 C lassLoader
		// 第二个参数：创建的代理实例所要实现的一些接口
		// 第三个参数：产生代理之后，调用代理里面的方法时，用哪一个 handler 处理。 
		UserDAO userDAOProxy = (UserDAO)Proxy.newProxyInstance(userDAO.getClass().getClassLoader(), userDAO.getClass().getInterfaces(), li);
		System.out.println(userDAOProxy.getClass());
		// 这样，这个代理实例就实现了目标业务类的所有接口
		
		// 操作代理实例
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
