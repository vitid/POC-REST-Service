package com.server.db.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class UserOrderDAO {
	private SessionFactory sessionFactory;
	private Transaction transaction;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void save(UserOrder order){
		Session session = sessionFactory.openSession();
		try{
			transaction = session.beginTransaction();
			session.save(order);
			transaction.commit();
		}catch(HibernateException e){
			if(transaction!=null)transaction.rollback();
			throw e;
		}finally{
			session.close();
		}
	}
	
	public void update(UserOrder order){
		Session session = sessionFactory.openSession();
		try{
			transaction = session.beginTransaction();
			session.update(order);
			transaction.commit();
		}catch(HibernateException e){
			if(transaction!=null)transaction.rollback();
			throw e;
		}finally{
			session.close();
		}
	}
	
	public UserOrder get(int id){
		Session session = sessionFactory.openSession();
		try{
			Criteria criteria = session.createCriteria(UserOrder.class);
			criteria.add(Restrictions.eq("id",id));
			
			return (UserOrder)criteria.uniqueResult();
		}finally{
			session.close();
		}
	}
	
	public List<UserOrder> list(){
		Session session = sessionFactory.openSession();
		try{
			Criteria criteria = session.createCriteria(UserOrder.class);
			List<UserOrder> l = (List<UserOrder>)criteria.list();		
			return l;
		}finally{
			session.close();
		}
	}
}
