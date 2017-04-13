# Spring  

## HibernateTemplate

Spring事务的本质其实就是数据库对事务的支持。没有数据库的事务支持，spring是无法提供事务功能的。

对于纯JDBC操作数据库，想要用到事务，可以按照以下步骤进行：

1. 获取连接 Connection con = DriverManager.getConnection()
2. 开启事务con.setAutoCommit(true/false);
3. 执行CRUD
4. 提交事务/回滚事务 con.commit() / con.rollback();
5. 关闭连接 conn.close();

使用Spring的事务管理功能后，我们可以不再写步骤 2 和 4 的代码，而是由Spirng 自动完成。

大部分情况下，使用hibernate 的常规用法，就可完成大多数DAO对象的 CRUD操作。
按照以下步骤：

1. 在 Spring 中初始化 HibernateTemplate，注入 sessionFactory；

```
<bean id="hibernateTemplate" class="org.springframework.orm.hibernate3.HibernateTemplate">
	<property name="sessionFactory" ref="sessionFactory"></property>
 </bean>
```

2. 在 DAO 里注入 HibernateTemplate；

例如 UserDAOImpl 中：

```
private HibernateTemplate hibernateTemplate;

public HibernateTemplate getHibernateTemplate() {
	return hibernateTemplate;
}

@Resource
public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
	this.hibernateTemplate = hibernateTemplate;
}
```

3. 在 save 里写 getHibernateTemplate.save();

```
public void save(User user) {		
		hibernateTemplate.save(user);			
	//throw new RuntimeException("exeption!");
}
```

## HibernateTemplate 如何写

MyHibernateTemplate：

```
public class MyHibernateTemplate {

    public void executeWithNativeSession(MyHibernateCallback callback) {
	Session s = null;
	try {
	    s = getSession();
            s.beginTransaction();
		
	    callback.doInHibernate(s);
			
	    s.getTransaction().commit();
	} 
	    
	catch (Exception e) {
	    s.getTransaction().rollback();
	} 
	    
	finally {
            //...
	}
    }

    private Session getSession() {
	// TODO Auto-generated method stub
	return null;	
    }
	
    public void save(final Object o) {	
        new MyHibernateTemplate().executeWithNativeSession(new MyHibernateCallback() {
	    public void doInHibernate(Session s) {
	        s.save(o);		
	    }
	});
    }		
}
```




