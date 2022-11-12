package com.yll.service.impl;

import com.yll.dao.BaseDao;
import com.yll.dao.BillDao;
import com.yll.dao.ProviderDao;
import com.yll.dao.impl.BillDaoImpl;
import com.yll.dao.impl.ProviderDaoImpl;
import com.yll.pojo.Provider;
import com.yll.service.ProviderService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProviderServiceImpl extends BaseDao implements ProviderService {
	private ProviderDao providerDao;
	private BillDao billDao;

	public ProviderServiceImpl() {
		providerDao = new ProviderDaoImpl();
		billDao = new BillDaoImpl();
	}

	@Override
	public List<Provider> getProviderList(String proName, String proCode) {
		BaseDao.generateConn();
		List<Provider> providerList = null;
		try {
			providerList = providerDao.getProviderList(proName, proCode);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeConn();
		}
		return providerList;
	}

	@Override
	public boolean add(Provider provider) {
		boolean flag = false;
		try {
			BaseDao.generateConn();
			if (providerDao.add(provider) > 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeConn();
		}
		return flag;
	}

	@Override
	public int deleteProviderById(String delId) {

		int billCount = -1;
		try {
			BaseDao.generateConn();
			conn.setAutoCommit(false);//用不上事务
			billCount = billDao.getBillCountByProviderId(delId);
			if (billCount == 0) {
				providerDao.deleteProviderById(delId);
			}
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			billCount = -1;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			BaseDao.closeConn();
		}
		return billCount;
	}

	@Override
	public boolean modify(Provider provider) {

		boolean flag = false;
		try {
			BaseDao.generateConn();
			if (providerDao.modify(provider) > 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeConn();
		}
		return flag;
	}

	@Override
	public Provider getProviderById(String id) {
		Provider provider = null;

		try {
			BaseDao.generateConn();
			provider = providerDao.getProviderById(id);
		} catch (Exception e) {
			e.printStackTrace();
			provider = null;
		} finally {
			BaseDao.closeConn();
		}
		return provider;
	}


}
