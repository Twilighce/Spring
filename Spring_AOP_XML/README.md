
```java
 <?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
           http://www.springframework.org/schema/context
           http://www.springframework.org/schema/context/spring-context-2.5.xsd
           http://www.springframework.org/schema/aop
           http://www.springframework.org/schema/aop/spring-aop-2.5.xsd">
	<context:annotation-config />
	<context:component-scan base-package="com.Twilighce"/>

	<bean id="logInterceptor" class="com.Twilighce.aop.LogInterceptor"></bean>  
	
	<aop:config>
		
		<aop:pointcut
		    expression="excution(public * con.Twilighce.service..*.add(..))" id = "servicePoinrcut" />
		<aop:aspect id="logAspect" ref="logInterceptor">
		    <aop:before method="before" pointcut-ref="servicePointcut" />
		</aop:aspect>
		
	</aop:config>

</beans>
```

----------

 - 首先声明一个切面，在 service 的 add 方法上加切进来的逻辑，给这个切面起名 servicePointcut；    

 - 声明这个切面的对象 logAspect，它所参考的切面类的对象是 logInterceptor，
method = "before"；

 - 当找到执行时符合  public * con.Twilighce.service..*.add(..)  语法要求的方法的时候，它会在 before 执行之前，执行 logInterceptor  的 before 方法。

----------

 - Spring 首先通过 component-scan 找到 UserDAO，把它初始化成一个对象；  

 - 再初始化 logInerceptor 对象；    

 - 发现配置了 AOP ，  

 - 在执行到 UserServiceTest 的  service.add() 时，调用 service 的 save 方法，  
  
 - Spring 发现符合 execution(public * com.Twilighce.service..*.add(..)) 表达式，  

 - 在这个 表达式上有一个切面，这个切面引用了一个切面类的对象 logInterceptor，要求在这个方法执行之前，先执行 logInterceptor  的 before 方法。

----------

另一种写法：

```java
<bean id="logInterceptor" class="com.Twilighce.aop.LogInterceptor"></bean>
<aop:config>
		
	<aop:aspect id="logAspect" ref="logInterceptor">
		<aop:before method="before" pointcut="execution(public * com.Twilighce.service..*.add(..))" />
	</aop:aspect>
		
</aop:config>
```
