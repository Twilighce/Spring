# Spring  

这里使用 xml 的方式配置声明式的事务管理。

## **xml声明式事务管理**

在配置完 sessionFactory 之后：

按如下格式：

```
<!-- 定义事务管理器（声明式的事务） -->  
1. <bean txmanager

2. <aop:config
     1) <aop:pointcut
     2) <aop:advisor pointcut-ref advice-ref
     
3. <tx:advice: id transaction-manager=
```

具体到此例：

```
<bean id="txManager"
	class="org.springframework.orm.hibernate3.HibernateTransactionManager">
	<property name="sessionFactory" ref="sessionFactory" />
</bean>

<aop:config>
	<aop:pointcut id="bussinessService"
		expression="execution(public * com.bjsxt.service..*.*(..))" />
	<aop:advisor pointcut-ref="bussinessService"
		advice-ref="txAdvice" />
</aop:config>

<tx:advice id="txAdvice" transaction-manager="txManager">
	<tx:attributes>
		<tx:method name="getUser" read-only="true" />
		<tx:method name="add*" propagation="REQUIRED"/>
	</tx:attributes>	
</tx:advice>
```
