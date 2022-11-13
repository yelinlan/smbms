package com.yll.service.impl;

import com.yll.dao.BaseDao;
import com.yll.dao.RoleDao;
import com.yll.dao.impl.RoleDaoImpl;
import com.yll.pojo.Role;
import com.yll.service.RoleService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoleServiceImpl extends BaseDao implements RoleService {

	private RoleDao roleDao;

	public RoleServiceImpl() {
		roleDao = new RoleDaoImpl();
	}

	@Override
	public List<Role> queryList() {

		BaseDao.generateConn();
		List<Role> roleList = null;
		try {
			roleList = roleDao.getRoleList();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeConn();
		}
		return roleList;

	}
}