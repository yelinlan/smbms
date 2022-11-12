package com.yll.service;

import com.yll.pojo.Provider;

import java.util.List;

public interface ProviderService {
	List<Provider> getProviderList(String proName, String proCode);

	boolean add(Provider provider);

	int deleteProviderById(String delId);

	boolean modify(Provider provider);

	Provider getProviderById(String id);
}
