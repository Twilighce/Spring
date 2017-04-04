
## **使用 Annotation 做 AOP 配置**

 - 加上对应的 xsd 文件：spring-aop.xsd；  

 - 要想使用 AspectJ 语法，在 beans.xml 中加 <aop:aspect-autoproxy />， 自动产生代理；    

 - 加入需要的类库：aspectjrt.jar、 aspectjweaver.jar；    

 - 需要把 LogInterceptor 逻辑加到 UserDAO 的实现上：实际上是产生一个代理，在这个代理上有这个 LogInterceptor 逻辑，再调用 UserDAO 的实现。把这个逻辑**织入到**原来的方法里面。具体：  

    - 建立我们的拦截类 LogInterceptor；    

    - 用 @Aspect 注解这个类；  

    - 建立处理方法；  

    - 用 @Before 注解这个方法；  

    - 写明白切入点（excution......）；  

    - 让 Spring 对我们的拦截类进行管理。  

 - 像这样：
```java
@Aspect
@Component
public class LogInterceptor {

	// 定义了一系列的切入点集合，取名 myMethod
	@Pointcut("execution(public * com.Twilighce.service..*.add(..))")
	public void myMethod(){};
	
	// 相当于引用了名为 myMythod 的 PointCut
	@Before("myMethod()")
	public void before() {
		System.out.println("method before");
	}
	
	@Around("myMethod()")
	public void aroundMethod(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("method around start");
		pjp.proceed();
		System.out.println("method around end");
	}
	
}
```


 - 注意：  

    - 想把该逻辑织入到某个类的某个方法上，那个对象必须得是 Spring 管理的，而不能是自己 new 出来的。  

    - 织入点语法。
    - eg：

```java
@Before("excution(public void com.Twilighce.dao.impl.UserDAOImpl.save(com.Twilighce.model.User))")
public void before() {
}
```
   
在 com.Twilighce.dao.impl.UserDAOImpl 中的 save 方法执行之前，先把后面的 before 方法加到前面。

 - eg：

```java
@Before("excution(public * com.Twilighce.dao....*.*(..))")
public void before() {
}
```

 com.Twiighce.dao 下面，包括子包下面，任何类的任何方法，返回值可以是任何类型。

----------

