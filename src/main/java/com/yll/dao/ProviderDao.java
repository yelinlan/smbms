package com.yll.dao;


import com.yll.pojo.Provider;

import java.util.List;

public interface ProviderDao {
	List<Provider> getProviderList(String proName, String proCode) throws Exception;

	int add(Provider provider) throws Exception;

	int deleteProviderById(String delId) throws Exception;

	int modify(Provider provider) throws Exception;

	Provider getProviderById(String id) throws Exception;
}
