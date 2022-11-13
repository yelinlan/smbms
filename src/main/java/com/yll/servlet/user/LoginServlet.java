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

public class LoginServlet extends BaseServlet {

	private UserService userService;

	public LoginServlet() {
		this.userService = new UserServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		request = req;
		response = resp;
		System.out.println("login....");
		String userCode = req.getParameter("userCode");
		String userPassword = req.getParameter("userPassword");
		User user = userService.login(userCode, userPassword);
		if (user != null) {
			req.getSession().setAttribute(Constant.USER_SESSION, user);
			if (user.getUserPassword().equals(userPassword)) {
				System.out.println("success...");
				resp.sendRedirect(req.getContextPath()+"/jsp/frame.jsp");
			} else {
				req.setAttribute("error","用户名或密码错误");
				dispatcher("/login.jsp");
			}
		}
	}
}