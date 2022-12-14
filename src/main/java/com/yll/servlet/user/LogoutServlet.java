package com.yll.servlet.user;

import com.yll.constant.Constant;
import com.yll.pojo.User;
import com.yll.service.UserService;
import com.yll.service.impl.UserServiceImpl;
import com.yll.servlet.common.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LogoutServlet extends BaseServlet {

	private UserService userService;

	public LogoutServlet() {
		this.userService = new UserServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		request = req;
		response = resp;
		req.getSession().removeAttribute(Constant.USER_SESSION);
		redirect("/login.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		 super.doGet(req, resp);
	}
}