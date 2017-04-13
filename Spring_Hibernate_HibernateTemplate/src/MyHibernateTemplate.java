import org.hibernate.Session;


public class MyHibernateTemplate {
	public void executeWithNativeSession(MyHibernateCallback callback) {
		Session s = null;
		try {
			s = getSession();
			s.beginTransaction();
			
			callback.doInHibernate(s);
			
			s.getTransaction().commit();
		} catch (Exception e) {
			s.getTransaction().rollback();
		} finally {
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

