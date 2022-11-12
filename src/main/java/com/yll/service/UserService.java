package com.yll.service;

import com.yll.pojo.User;
import com.yll.util.PageSupport;

import java.util.List;

/**
 *@项目名称: smbms
 *@类名称: UserService
 *@类描述:
 *@创建人: quanyixiang
 *@创建时间: 2022/11/10 19:59
 **/
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