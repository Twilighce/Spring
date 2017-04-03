package com.Twilighce.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogInterceptor implements InvocationHandler {
	
	//保存一个被代理对象
	private Object target;
	
	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}

	public void beforeMethod(Method m) {
		
		System.out.println(m.getName() + " start");
	}

	// InvocationHandler接口定义了 invoke(Object proxy, Method method, Object[] args)方法
	// proxy是代理实例，一般不会用到；
	// method是代理实例上的方法，通过它可以发起对目标类的反射调用；
	// args是通过代理类传入的方法参数，在反射调用时使用。
	public Object invoke(Object proxy, Method m, Object[] args)
			throws Throwable {
		beforeMethod(m);
		m.invoke(target, args);
		return null;
	}
}
 