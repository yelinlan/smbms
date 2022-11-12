package com.yll.servlet.user;

import cn.hutool.json.JSONUtil;
import com.mysql.cj.util.StringUtils;
import com.yll.constant.Constant;
import com.yll.pojo.Role;
import com.yll.pojo.User;
import com.yll.service.RoleService;
import com.yll.service.UserService;
import com.yll.service.impl.RoleServiceImpl;
import com.yll.service.impl.UserServiceImpl;
import com.yll.servlet.common.BaseServlet;
import com.yll.util.ConvertUtil;
import com.yll.util.ResultData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 *@项目名称: smbms
 *@类名称: UserServlet
 *@类描述:
 *@创建人: quanyixiang
 *@创建时间: 2022/11/11 20:07
 **/
public class UserServlet extends BaseServlet {

	private UserService userService;
	private RoleService roleService;

	public UserServlet() {
		this.userService = new UserServiceImpl();
		this.roleService = new RoleServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		request = req;
		response = resp;
		String method = request.getParameter(Constant.METHOD);
		if (method.equals("savepwd")) {
			savepwd("/jsp/pwdmodify.jsp");
		} else if (method.equals("pwdmodify")) {
			pwdModify();
		} else if (method.equals("query")) {
			query("/jsp/userlist.jsp");
		} else if (method.equals("view")) {
			modify("/jsp/userview.jsp");
		} else if (method.equals("modify")) {
			modify("/jsp/usermodify.jsp");
		} else if (method.equals("getrolelist")) {
			getRolelist();
		} else if (method.equals("modifyexe")) {
			modifyExe("usermodify.jsp", "/jsp/user.do?method=query");
		} else if (method.equals("ucexist")) {
			ucexist();
		} else if (method.equals("add")) {
			add("useradd.jsp");
		} else if (method.equals("deluser")) {
			deluser();
		}
	}

	private void deluser() {
		Long id = Long.valueOf(Objects.toString(request.getParameter("uid"), "0"));
		if (id <= 0) {
			resultData.setDelResult("notexist");
		} else {
			boolean flag = userService.deluserById(id);
			if (flag) {
				resultData.setDelResult("true");
			} else {
				resultData.setDelResult("false");
			}
		}
		result(JSONUtil.toJsonStr(resultData));
	}

	private void add(String url) {
		User user = ConvertUtil.convertBean(User.class, request);
		boolean flag = userService.insert(user);
		if (flag) {
			redirect("/jsp/user.do?method=query");
		} else {
			dispatcher(url);
		}
	}

	private void ucexist() {
		String userCode = request.getParameter("userCode");
		boolean flag = userService.ucexist(userCode);
		if (flag) {
			resultData.setUserCode(Constant.EXIST);
		}
		result(JSONUtil.toJsonStr(resultData));
	}

	private void modifyExe(String url, String url1) {
		User user = ConvertUtil.convertBean(User.class, request);
		user.setId(Long.valueOf(request.getParameter("uid")));
		boolean flag = userService.updateById(user);
		if (flag) {
			redirect(url1);
		} else {
			dispatcher(url);
		}
	}

	private void getRolelist() {
		List<Role> roleList = roleService.queryList();
		result(JSONUtil.toJsonStr(roleList));
	}

	private void modify(String url) {
		String uid = request.getParameter("uid");
		User user = userService.getById(uid);
		request.setAttribute("user", user);
		dispatcher(url);
	}

	private void query(String url) {
		String queryname = request.getParameter("queryname");
		String queryUserRole = request.getParameter("queryUserRole");
		int pageIndex = Integer.parseInt(Objects.toString(request.getParameter("pageIndex"), "0"));
		pageSupport.setTotalCount(userService.queryCount(queryname, queryUserRole));
		pageIndex = Math.min(pageIndex, pageSupport.getTotalCount());
		pageIndex = Math.max(pageIndex, 0);
		pageSupport.setCurrentPageNo(pageIndex);
		List<User> userList = userService.queryList(queryname, queryUserRole, pageSupport);
		List<Role> roleList = roleService.queryList();

		request.setAttribute("userList", userList);
		request.setAttribute("roleList", roleList);
		request.setAttribute("totalPageCount", pageSupport.getTotalPageCount());
		request.setAttribute("totalCount", pageSupport.getTotalCount());
		request.setAttribute("currentPageNo", pageSupport.getCurrentPageNo());
		dispatcher(url);
	}

	private void pwdModify() {
		Object userSession = request.getSession().getAttribute(Constant.USER_SESSION);
		String oldpassword = request.getParameter("oldpassword");

		if (userSession == null) {
			resultData.setResult("sessionerror");
		} else if (StringUtils.isNullOrEmpty(oldpassword)) {
			resultData.setResult("error");
		} else {
			String password = ((User) userSession).getUserPassword();
			if (oldpassword.equals(password)) {
				resultData.setResult("true");
			} else {
				resultData.setResult("false");
			}
		}

		result(JSONUtil.toJsonStr(resultData));
	}

	private void savepwd(String url) throws ServletException, IOException {
		Object userSession = request.getSession().getAttribute(Constant.USER_SESSION);
		String newpassword = request.getParameter("newpassword");
		boolean flag = false;
		if (userSession != null && !StringUtils.isNullOrEmpty(newpassword)) {
			User user = (User) userSession;
			flag = userService.updatePwd(user.getId(), newpassword);
			if (flag) {
				request.setAttribute("message", "“修改密码成功，请退出:使用新密码登录");
				request.getSession().removeAttribute(Constant.USER_SESSION);
			} else {
				request.setAttribute("message", "“修改密码失败");
			}
		} else {
			request.setAttribute("message", "“新密码有问题");
		}
		dispatcher(url);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}