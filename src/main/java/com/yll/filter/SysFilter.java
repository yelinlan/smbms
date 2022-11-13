package com.yll.filter;

import com.yll.constant.Constant;
import com.yll.pojo.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SysFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("CharacterEncodingFilter初始化...");
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("CharacterEncodingFilter过滤 ...");

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		User user = (User) request.getSession().getAttribute(Constant.USER_SESSION);
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/error.jsp");
		}else {
			chain.doFilter(req, resp);
		}
		System.out.println("CharacterEncodingFilter过滤完成");
	}

	@Override
	public void destroy() {
		System.out.println("CharacterEncodingFilter销毁");
		Filter.super.destroy();
	}
}