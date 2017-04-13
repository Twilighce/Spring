# Spring

学习下 Spring 的事务管理。

spring 的事务管理方式从大的方向上来讲，分为 **编程式事务** 和 **声明式事务**； 

编程式事务就是实现 PlatformTransactionManager 接口 和 TransactionTemplate 两种方式。 

这里先讲 声明式事务管理。

## 声明式事务管理

Spring 的声明式事务管理 是通过AOP实现的。
大多数Spring用户选择声明式事务管理，这是最少影响应用代码的选择，因而这是和非侵入性的轻量级容器的观念是一致的。

Spring配置文件 applicationContext.xml 中关于事务配置总是由三个组成部分，分别是DataSource、TransactionManager和代理机制这三部分，无论哪种配置方式，一般变化的只是代理机制这部分。 

DataSource、TransactionManager这两部分只是会根据数据访问方式有所变化：  

比如使用 hibernate 进行数据访问时，DataSource实际为SessionFactory，TransactionManager的实现为 HibernateTransactionManager。

如下：

![enter image description here](http://oimbmvqt3.bkt.clouddn.com/%E4%BA%8B%E5%8A%A1%E7%AE%A1%E7%90%86.PNG)

定事务的时候，需要定出事务的边界，哪里开始哪里结束。

在这个例子中，事务加在 DAO 层还是 Service 层呢？

最直观的，是加在 DAO层，直接跟数据库打交道。

但是，DAO 只是针对某种特定实体做增删改查操作，比如 UserDAO 就只针对 User.

如果我们现在要求，每次存一个 User 之后，在数据库里的另一张表里记一条日志：a user saved! 

新建 LogDAO 对日志进行操作。

这样把事务加在 DAO 层，就不合适了。因为，比如 UserDAO 操作完成了，在此期间遇到异常，本来正常情况下，UserDAO 应该回滚，但现在无法回滚了。

所以，事务应当加在 Service 层的方法里面。

那么，事务该怎么加呢？

## @Transaction

在一个方法上加事务，加上注解：

>@Transactional

使用的时候，新加一个 namespace：

>xmlns:tx="http://www.springframework.org/schema/tx"

同时，在 xml 文件中声明，现在是 annotation 驱动的 Transaction 管理：

><tx:annotation-driven transaction-manager="txManager"/>

来看 HibernateTransactionManager：

```java
<!-- 定义事务管理器（声明式的事务） -->
<bean id="txManager"
	class="org.springframework.orm.hibernate3.HibernateTransactionManager">
	<property name="sessionFactory" ref="sessionFactory" />
</bean>
```
HibernateTransactionManager 这个类，类似于一个 Aspect，在方法前后加点东西。 这里应用了 AOP。
让它通过数据库的连接来管理事务，要告诉它数据库的连接是谁，就要把 sessionFactory 注入，sessionFactory 里，又被注入了 dataSource。
TransactionManager 在管理事务时，需要 Hibernate 的一些配置，这些配置 sessionFactory 中都有。

## 什么时候回滚？

分析下什么时候 rollback？

1. 运行期异常。非运行期异常不会 rollbak；  

2. 必须 uncheck （没有 catch）；  

3. 不管什么异常，只要 catch 了，Spring 就会放弃管理；  

4. propagation_required；
  
>如果ServiceB.methodB() 的事务级别定义为 PROPAGATION_REQUIRED，那么执行 ServiceA.methodA() 的时候spring已经起了事务，这时调用 ServiceB.methodB()，ServiceB.methodB() 看到自己已经运行在 ServiceA.methodA() 的事务内部，就不再起新的事务。
>假如 ServiceB.methodB() 运行的时候发现自己没有在事务中，他就会为自己分配一个事务。
>这样，在 ServiceA.methodA() 或者在 ServiceB.methodB() 内的任何地方出现异常，事务都会被回滚。
  
5. read_only。


