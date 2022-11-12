package com.yll.pojo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @TableName smbms_user
 */
@Data
public class User implements Serializable {
	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 用户编码
	 */
	private String userCode;

	/**
	 * 用户名称
	 */
	private String userName;

	/**
	 * 用户密码
	 */
	private String userPassword;

	/**
	 * 性别（1:女、 2:男）
	 */
	private Integer gender;

	/**
	 * 出生日期
	 */
	private LocalDateTime birthday;

	/**
	 * 手机
	 */
	private String phone;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 用户角色（取自角色表-角色id）
	 */
	private Integer userRole;

	/**
	 * 创建者（userId）
	 */
	private Long createdBy;

	/**
	 * 创建时间
	 */
	private LocalDateTime creationDate;

	/**
	 * 更新者（userId）
	 */
	private Long modifyBy;

	/**
	 * 更新时间
	 */
	private LocalDateTime modifyDate;

	private static final long serialVersionUID = 1L;

	private Integer age;
	private String userRoleName;

	public Integer getAge() {
		return birthday != null ? LocalDateTime.now().getYear() - birthday.getYear() : null;
	}

}