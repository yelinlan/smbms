package com.yll.dao.impl;


import com.yll.dao.BaseDao;
import com.yll.dao.ProviderDao;
import com.yll.pojo.Provider;
import com.yll.util.ConvertUtil;
import com.yll.util.SqlUtil;

import java.util.List;

/**
 * @author Administrator
 * @Auther: wuxy
 * @Date: 2021/2/2 - 02 - 02 - 18:34
 * @Description: com.wxy.dao.provider
 * @version: 1.0
 */
public class ProviderDaoImpl extends BaseDao implements ProviderDao {

	@Override
	public List<Provider> getProviderList(String proName, String proCode) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from smbms_provider ");
		SqlUtil sqlUtil = SqlUtil.instance().like("proName", proName).like("proCode", proCode).head(sql);
		List<Provider> providerList = ConvertUtil.convertBeans(Provider.class, executeQuery(sql, sqlUtil.getParams()));
		BaseDao.close();
		return providerList;
	}

	@Override
	public int add(Provider provider) throws Exception {
		int flag = 0;
		StringBuffer sql = new StringBuffer(" insert into smbms_provider(%s) values(%s) ");
		SqlUtil sqlUtil = SqlUtil.instance().insertBeans(provider).merge(sql);
		flag = BaseDao.executeUpdate(sql, sqlUtil.getParams());
		BaseDao.close();
		return flag;
	}

	@Override
	public int deleteProviderById(String delId) throws Exception {
		int flag = 0;
		StringBuffer sql = new StringBuffer("delete from smbms_provider ");
		SqlUtil sqlUtil = SqlUtil.instance().and("id", delId).head(sql);
		flag = BaseDao.executeUpdate(sql, sqlUtil.getParams());
		BaseDao.close();
		return flag;
	}

	@Override
	public int modify(Provider provider) throws Exception {
		int flag = 0;
		StringBuffer sql = new StringBuffer("update smbms_provider ");
		SqlUtil sqlUtil = SqlUtil.instance().setBeans(provider).and("id", provider.getId()).head(sql);
		flag = BaseDao.executeUpdate(sql, sqlUtil.getParams());
		BaseDao.close();
		return flag;
	}

	@Override
	public Provider getProviderById(String id) throws Exception {
		StringBuffer sql = new StringBuffer("select * from smbms_provider ");
		SqlUtil sqlUtil = SqlUtil.instance().and("id", id).head(sql);
		Provider provider = ConvertUtil.convertBean(Provider.class, BaseDao.executeQuery(sql, sqlUtil.getParams()));
		BaseDao.close();
		return provider;
	}

}
