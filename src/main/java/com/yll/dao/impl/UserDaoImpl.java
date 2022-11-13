package com.yll.dao.impl;

import com.yll.dao.BaseDao;
import com.yll.dao.UserDao;
import com.yll.pojo.User;
import com.yll.util.ConvertUtil;
import com.yll.util.PageSupport;
import com.yll.util.SqlUtil;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl extends BaseDao implements UserDao {
	@Override
	public User getLoginUser(String userCode) throws SQLException {
		StringBuffer sql = new StringBuffer(" select * from smbms_user  ");
		SqlUtil sqlUtil = SqlUtil.instance().and("userCode", userCode).head(sql);
		User user = ConvertUtil.convertBean(User.class, executeQuery(sql, sqlUtil.getParams()));
		close();
		return user;
	}

	@Override
	public int updatePwd(long id, String password) throws SQLException {
		StringBuffer sql = new StringBuffer(" update smbms_user ");
		SqlUtil sqlUtil = SqlUtil.instance().set("userPassword", password).and("id", id).head(sql);
		int execute = executeUpdate(sql, sqlUtil.getParams());
		close();
		return execute;
	}

	@Override
	public List<User> queryList(String queryname, String queryUserRole, PageSupport pageSupport) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*,b.roleName as userRoleName ");
		sql.append(" from smbms_user a left join smbms_role b on a.userRole = b.id ");
		SqlUtil sqlUtil = SqlUtil.instance().like("a.userName", queryname)
				.and(!"0".equals(queryUserRole), "b.id", queryUserRole).limit(pageSupport).head(sql);
		List<User> userList = ConvertUtil.convertBeans(User.class, executeQuery(sql, sqlUtil.getParams()));
		close();
		return userList;
	}

	@Override
	public int queryCount(String queryname, String queryUserRole) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select count(*) as total ");
		sql.append(" from smbms_user a left join smbms_role b on a.userRole = b.id ");
		SqlUtil sqlUtil = SqlUtil.instance().and("a.userName", queryname)
				.and(!"0".equals(queryUserRole), "b.id", queryUserRole).head(sql);
		int totalCount = ConvertUtil.convertCount(executeQuery(sql, sqlUtil.getParams()), "total");
		close();
		return totalCount;
	}

	@Override
	public User getById(String uid) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select a.*,b.roleName as userRoleName ");
		sql.append(" from smbms_user a left join smbms_role b on a.userRole = b.id ");
		SqlUtil sqlUtil = SqlUtil.instance().and("a.id", uid).head(sql);
		User user = ConvertUtil.convertBean(User.class, executeQuery(sql, sqlUtil.getParams()));
		close();
		return user;
	}

	@Override
	public void updateById(User user) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" update  smbms_user ");
		SqlUtil sqlUtil = SqlUtil.instance().setBeans(user).and(" id ", user.getId()).head(sql);
		executeUpdate(sql, sqlUtil.getParams());
		close();
	}

	@Override
	public void insert(User user) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into smbms_user(%s) values(%s) ");
		SqlUtil sqlUtil = SqlUtil.instance().insertBeans(user).merge(sql);
		executeUpdate(sql, sqlUtil.getParams());
		close();
	}

	@Override
	public int deluserById(Long id) throws SQLException {
		StringBuffer sql = new StringBuffer();
		sql.append(" delete from smbms_user  ");
		SqlUtil sqlUtil = SqlUtil.instance().and(" id ", id).head(sql);
		int del = executeUpdate(sql, sqlUtil.getParams());
		close();
		return del;
	}
}