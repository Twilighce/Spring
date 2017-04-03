package com.Twilighce.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LogInterceptor implements InvocationHandler {
	
	//����һ�����������
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

	// InvocationHandler�ӿڶ����� invoke(Object proxy, Method method, Object[] args)����
	// proxy�Ǵ���ʵ����һ�㲻���õ���
	// method�Ǵ���ʵ���ϵķ�����ͨ�������Է����Ŀ����ķ�����ã�
	// args��ͨ�������ഫ��ķ����������ڷ������ʱʹ�á�
	public Object invoke(Object proxy, Method m, Object[] args)
			throws Throwable {
		beforeMethod(m);
		m.invoke(target, args);
		return null;
	}
}
 