package com.server.db.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class ProductDAO {
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Product get(int id){
		Session session = sessionFactory.openSession();
		try{
			Criteria criteria = session.createCriteria(Product.class);
			criteria.add(Restrictions.eq("id",id));
			
			return (Product)criteria.uniqueResult();
		}finally{
			session.close();
		}
	}
	
	public List<Product> list(){
		Session session = sessionFactory.openSession();
		try{
			Criteria criteria = session.createCriteria(Product.class);
			return (List<Product>)criteria.list();
		}finally{
			session.close();
		}
	}

}
