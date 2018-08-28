package com.przemo.hibernate.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.przemo.databaseclasses.Params;

public class ParamsDao implements ParamsDaoInterface<Params, String>
{
	
private Session currentSession;
	
	private Transaction currentTransaction;

	public ParamsDao() {}
	
	public Session openCurrentSession() {
		currentSession = getSessionFactory().openSession();
		return currentSession;
	}

	public Session openCurrentSessionwithTransaction() {
		currentSession = getSessionFactory().openSession();
		currentTransaction = currentSession.beginTransaction();
		return currentSession;
	}
	
	public void closeCurrentSession() {
		currentSession.close();
	}
	
	public void closeCurrentSessionwithTransaction() {
		currentTransaction.commit();
		currentSession.close();
	}
	
	private static SessionFactory getSessionFactory() {
		Configuration configuration = new Configuration().configure();
		StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());
		SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());
		return sessionFactory;
	}

	public Session getCurrentSession() {
		return currentSession;
	}

	public void setCurrentSession(Session currentSession) {
		this.currentSession = currentSession;
	}

	public Transaction getCurrentTransaction() {
		return currentTransaction;
	}

	public void setCurrentTransaction(Transaction currentTransaction) {
		this.currentTransaction = currentTransaction;
	}

	@Override
	public void persist(Params entity) 
	{
		getCurrentSession().save(entity);
	}

	@Override
	public void update(Params entity) 
	{
		getCurrentSession().update(entity);
	}

	@Override
	public Params findById(String id) 
	{
		Params params = (Params) getCurrentSession().get(Params.class, id);
		return params; 
	}

	@Override
	public void delete(Params entity)
	{
		getCurrentSession().delete(entity);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Params> findAll() 
	{
		List<Params> params = (List<Params>) getCurrentSession().createQuery("from Params").list();
		return params;
	}

	@Override
	public void deleteAll() 
	{
		List<Params> entityList = findAll();
		for (Params entity : entityList) {
			delete(entity);
		}
	}

}
