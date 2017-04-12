# Spring  

**AOP 织入切面的方法：**

1. 编译期织入，要求使用特殊的Java编译器，AspectJ是其中的代表者；
2. 类装载期织入，要求使用特殊的类装载器，AspectJ和AspectWerkz是其中的代表者；
3. 动态代理织入，在运行期为目标类添加增强生成子类的方式，spring AOP采用动态代理织入切面。

---

**Spring AOP使用了两种代理机制：** 

1. 基于JDK的动态代理，
2. 基于CGLib的动态代理， 

之所以需要两种代理机制，很大程度上是因为JDK本身只提供基于接口的代理，不支持类的代理。 

---

**JDK动态代理：**

涉及到 java.lang.reflect 包中的两个类：

- Proxy
   - 为 InvocationHandler 实现类动态创建一个符合某一接口的代理实例。

- InvocationHandler
   - 一个接口，可以通过实现该接口定义横切逻辑，并通过反射机制调用目标类的代码，动态地将横切逻辑和业务逻辑织在一起。  
 
---
 
这里给出一个JDK 动态代理的例子。  


将横切代码放到 InvocationHandler 中。

```java
public class LogInterceptor implements InvocationHandler {
	
	// target 为目标的业务类
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

	// InvocationHandler接口定义了 invoke(Object proxy, Method method, Object[] args)方法，将横切代码和目标业务类代码编织到一起。
	// proxy是代理实例，一般不会用到；
	// method是代理实例上的方法，通过它可以发起对目标类的反射调用；
	// args是通过代理类传入的方法参数，在反射调用时使用。
	public Object invoke(Object proxy, Method m, Object[] args)
			throws Throwable {
		beforeMethod(m);
		m.invoke(target, args); //通过反射方法调用目标业务类的业务方法
		return null;
	}
} 
```

解释下：

首先，实现 InvocationHandler 接口，该接口定义了一个 invoke(Object proxy, Method method, Object[] args) 的方法，proxy是代理实例，一般不会用到；method是代理实例上的方法，通过它可以发起对目标类的反射调用；args是通过代理类传入的方法参数，在反射调用时使用。
    
此外，我们通过target传入真实的目标对象，在接口方法 invoke(Object proxy, Method method, Object[] args) 里，将目标类实例传给 method.invoke() 方法，通过反射调用目标类方法。

