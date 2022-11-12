package com.yll.service.impl;

import com.mysql.cj.util.StringUtils;
import com.yll.dao.BaseDao;
import com.yll.dao.UserDao;
import com.yll.dao.impl.UserDaoImpl;
import com.yll.pojo.User;
import com.yll.service.UserService;
import com.yll.util.PageSupport;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 *@项目名称: smbms
 *@类名称: UserServiceImpl
 *@类描述:
 *@创建人: quanyixiang
 *@创建时间: 2022/11/10 19:59
 **/
public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public UserServiceImpl() {
		userDao = new UserDaoImpl();
	}

	@Override
	public User login(String userCode, String password) {
		BaseDao.generateConn();
		User user = null;
		try {
			user = userDao.getLoginUser(userCode);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeConn();
		}
		return user;
	}

	@Override
	public boolean updatePwd(Long id, String password) {
		boolean flag = false;
		try {
			BaseDao.generateConn();
			if (userDao.updatePwd(id, password) > 0) {
				flag = true;
			}
		} catch (SQLException throwables) {
			throwables.printStackTrace();
		} finally {
			BaseDao.closeConn();
		}
		return flag;
	}

	@Override
	public List<User> queryList(String queryname, String queryUserRole, PageSupport pageSupport) {
		BaseDao.generateConn();
		List<User> userList = null;
		try {
			userList = userDao.queryList(queryname, queryUserRole, pageSupport);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeConn();
		}
		return userList;

	}

	@Override
	public int queryCount(String queryname, String queryUserRole) {
		BaseDao.generateConn();
		int count = 0;
		try {
			count = userDao.queryCount(queryname, queryUserRole);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeConn();
		}
		return count;
	}

	@Override
	public User getById(String uid) {
		BaseDao.generateConn();
		User user = new User();
		try {
			user = userDao.getById(uid);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeConn();
		}
		return user;
	}

	@Override
	public boolean updateById(User user) {
		BaseDao.generateConn();
		boolean flag = false;
		try {
			userDao.updateById(user);
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeConn();
		}
		return flag;

	}

	@Override
	public boolean ucexist(String userCode) {
		BaseDao.generateConn();
		if (StringUtils.isNullOrEmpty(userCode)) {
			return true;
		}
		User user = null;
		try {
			user = userDao.getLoginUser(userCode);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeConn();
		}
		return user != null && user.getId() != null;
	}

	@Override
	public boolean insert(User user) {
		BaseDao.generateConn();
		boolean flag = false;
		try {
			userDao.insert(user);
			flag = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeConn();
		}
		return flag;
	}

	@Override
	public boolean deluserById(Long id) {
		BaseDao.generateConn();
		boolean flag = false;
		try {
			flag = userDao.deluserById(id) == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			BaseDao.closeConn();
		}
		return flag;
	}
}