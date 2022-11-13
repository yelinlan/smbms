package com.yll.dao;

import com.yll.pojo.User;
import com.yll.util.PageSupport;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

	User getLoginUser(String userCode) throws SQLException;

	int updatePwd(long id, String password) throws SQLException;

	List<User> queryList(String queryname, String queryUserRole, PageSupport pageSupport) throws SQLException;

	int queryCount(String queryname, String queryUserRole) throws SQLException;

	User getById(String uid) throws SQLException;

	void updateById(User user) throws SQLException;

	void insert(User user) throws SQLException;

	int deluserById(Long id) throws SQLException;
}