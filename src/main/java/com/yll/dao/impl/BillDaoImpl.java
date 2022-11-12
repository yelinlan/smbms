package com.yll.dao.impl;

import com.yll.dao.BaseDao;
import com.yll.dao.BillDao;
import com.yll.pojo.Bill;
import com.yll.util.ConvertUtil;
import com.yll.util.SqlUtil;

import java.sql.ResultSet;
import java.util.List;

public class BillDaoImpl extends BaseDao implements BillDao {
	@Override
	public int add(Bill bill) throws Exception {
		int flag = 0;
		StringBuffer sql = new StringBuffer("insert into smbms_bill (%s) values(%s)");
		SqlUtil sqlUtil = SqlUtil.instance().insertBeans(bill).merge(sql);
		flag = BaseDao.executeUpdate(sql, sqlUtil.getParams());
		BaseDao.close();
		return flag;
	}

	@Override
	public List<Bill> getBillList(Bill bill) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select b.*,p.proName as providerName from smbms_bill b inner join smbms_provider p on b.providerId = p.id");
		SqlUtil sqlUtil = SqlUtil.instance().like("productName", bill.getProductName())
				.and(bill.getProviderId() > 0, "providerId", bill.getProviderId())
				.and(bill.getIsPayment() > 0, "isPayment", bill.getIsPayment()).head(sql);
		List<Bill> billList = ConvertUtil.convertBeans(Bill.class, BaseDao.executeQuery(sql, sqlUtil.getParams()));
		BaseDao.close();
		return billList;
	}

	@Override
	public int deleteBillById(String delId) throws Exception {
		int flag = 0;
		StringBuffer sql = new StringBuffer("delete from smbms_bill ");
		SqlUtil sqlUtil = SqlUtil.instance().and("id", delId).head(sql);
		flag = BaseDao.executeUpdate(sql, sqlUtil.getParams());
		BaseDao.close();
		return flag;
	}

	@Override
	public Bill getBillById(String id) throws Exception {
		StringBuffer sql = new StringBuffer("select b.*,p.proName as providerName from smbms_bill b inner join smbms_provider p "
				+ "on b.providerId = p.id ");
		SqlUtil sqlUtil = SqlUtil.instance().and("b.id", id).head(sql);
		Bill bill = ConvertUtil.convertBean(Bill.class, BaseDao.executeQuery(sql, sqlUtil.getParams()));
		BaseDao.close();
		return bill;
	}

	@Override
	public int modify(Bill bill) throws Exception {
		int flag = 0;
		StringBuffer sql = new StringBuffer("update smbms_bill ");
		SqlUtil sqlUtil = SqlUtil.instance().setBeans(bill).and("id", bill.getId()).head(sql);
		flag = BaseDao.executeUpdate(sql, sqlUtil.getParams());
		BaseDao.close();
		return flag;
	}

	@Override
	public int getBillCountByProviderId(String providerId) throws Exception {
		StringBuffer sql = new StringBuffer("select count(1) as billCount from smbms_bill ");
		SqlUtil sqlUtil = SqlUtil.instance().and("providerId", providerId).head(sql);
		ResultSet rs = BaseDao.executeQuery(sql, sqlUtil.getParams());
		int count = ConvertUtil.convertCount(rs, "billCount");
		BaseDao.close();
		return count;
	}
}
