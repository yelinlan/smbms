package com.yll.dao.impl;

import com.yll.dao.BaseDao;
import com.yll.dao.RoleDao;
import com.yll.pojo.Role;
import com.yll.util.ConvertUtil;
import com.yll.util.SqlUtil;

import java.sql.SQLException;
import java.util.List;

public class RoleDaoImpl extends BaseDao implements RoleDao {
	@Override
	public List<Role> getRoleList() throws SQLException {
		List<Role> roleList = null;
		StringBuffer sql = new StringBuffer(" select b.*  from smbms_role b  ");
		SqlUtil sqlUtil = SqlUtil.instance().head(sql);
		roleList = ConvertUtil.convertBeans(Role.class, executeQuery(sql, sqlUtil.getParams()));
		close();
		return roleList;
	}
}