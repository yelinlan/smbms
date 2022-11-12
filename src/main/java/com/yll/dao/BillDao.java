package com.yll.dao;


import com.yll.pojo.Bill;

import java.util.List;

public interface BillDao {
	int add(Bill bill) throws Exception;

	List<Bill> getBillList(Bill bill) throws Exception;

	int deleteBillById(String delId) throws Exception;


	Bill getBillById(String id) throws Exception;

	int modify(Bill bill) throws Exception;

	int getBillCountByProviderId(String providerId) throws Exception;

}