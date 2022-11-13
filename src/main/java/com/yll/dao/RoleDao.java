package com.yll.dao;

import com.yll.pojo.Role;

import java.sql.SQLException;
import java.util.List;

public interface RoleDao {

	List<Role> getRoleList() throws SQLException;

}