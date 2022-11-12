package com.yll.service.impl;


import com.yll.dao.BaseDao;
import com.yll.dao.BillDao;
import com.yll.dao.impl.BillDaoImpl;
import com.yll.pojo.Bill;
import com.yll.service.BillService;

import java.util.List;

public class BillServiceImpl extends BaseDao implements BillService {
	private BillDao billDao;

	public BillServiceImpl() {
		billDao = new BillDaoImpl();
	}

	@Override
	public boolean add(Bill bill) {
		boolean flag = false;
		try {
			BaseDao.generateConn();
			if (billDao.add( bill) > 0) {
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
	public List<Bill> getBillList(Bill bill) {
		List<Bill> billList = null;
		try {
			BaseDao.generateConn();
			billList = billDao.getBillList( bill);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeConn();
		}
		return billList;

	}

	@Override
	public boolean deleteBillById(String delId) {
		boolean flag = false;
		try {
			BaseDao.generateConn();
			if (billDao.deleteBillById( delId) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeConn();
		}
		return flag;
	}

	@Override
	public Bill getBillById(String id) {
		Bill bill = null;
		try {
			BaseDao.generateConn();
			bill = billDao.getBillById( id);
		} catch (Exception e) {
			e.printStackTrace();
			bill = null;
		} finally {
			BaseDao.closeConn();
		}
		return bill;
	}

	@Override
	public boolean modify(Bill bill) {
		boolean flag = false;
		try {
			BaseDao.generateConn();
			if (billDao.modify( bill) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeConn();
		}
		return flag;
	}
}
