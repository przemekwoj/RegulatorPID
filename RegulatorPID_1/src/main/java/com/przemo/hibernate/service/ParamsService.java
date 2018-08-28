package com.przemo.hibernate.service;

import java.util.List;

import com.przemo.databaseclasses.Params;
import com.przemo.hibernate.dao.ParamsDao;

public class ParamsService
{
	private static ParamsDao paramsDao;

	public ParamsService() {
		paramsDao = new ParamsDao();
	}

	public void persist(Params entity) {
		paramsDao.openCurrentSessionwithTransaction();
		paramsDao.persist(entity);
		paramsDao.closeCurrentSessionwithTransaction();
	}

	public void update(Params entity) {
		paramsDao.openCurrentSessionwithTransaction();
		paramsDao.update(entity);
		paramsDao.closeCurrentSessionwithTransaction();
	}

	public Params findById(String id) {
		paramsDao.openCurrentSession();
		Params params = paramsDao.findById(id);
		paramsDao.closeCurrentSession();
		return params;
	}

	public void delete(String id) {
		paramsDao.openCurrentSessionwithTransaction();
		Params params = paramsDao.findById(id);
		paramsDao.delete(params);
		paramsDao.closeCurrentSessionwithTransaction();
	}

	public List<Params> findAll() {
		paramsDao.openCurrentSession();
		List<Params> books = paramsDao.findAll();
		paramsDao.closeCurrentSession();
		return books;
	}

	public void deleteAll() {
		paramsDao.openCurrentSessionwithTransaction();
		paramsDao.deleteAll();
		paramsDao.closeCurrentSessionwithTransaction();
	}

	public ParamsDao bookDao() {
		return paramsDao;
	}
}
