package com.yll.service;

import com.yll.pojo.User;
import com.yll.util.PageSupport;

import java.util.List;

public interface UserService {

	User login(String userCode, String password);

	boolean updatePwd(Long id, String password);

	List<User> queryList(String queryname, String queryUserRole, PageSupport pageSupport);

	int queryCount(String queryname, String queryUserRole);

	User getById(String uid);

	boolean updateById(User user);

	boolean ucexist(String userCode);

	boolean insert(User user);

	boolean deluserById(Long id);
}