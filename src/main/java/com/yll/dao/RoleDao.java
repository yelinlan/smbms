package com.yll.dao;

import com.yll.pojo.Role;

import java.sql.SQLException;
import java.util.List;

/**
 *@项目名称: smbms
 *@类名称: UserDao
 *@类描述:
 *@创建人: quanyixiang
 *@创建时间: 2022/11/10 19:44
 **/
public interface RoleDao {

	List<Role> getRoleList() throws SQLException;

}