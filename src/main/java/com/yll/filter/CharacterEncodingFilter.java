package com.yll.filter;

import com.yll.constant.Constant;

import javax.servlet.*;
import java.io.IOException;

public class CharacterEncodingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("CharacterEncodingFilter初始化...");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("CharacterEncodingFilter过滤 ...");
		req.setCharacterEncoding(Constant.UTF_8);
		resp.setCharacterEncoding(Constant.UTF_8);
		resp.setContentType(Constant.CONTENT_TYPE);
		chain.doFilter(req, resp);
		System.out.println("CharacterEncodingFilter过滤完成");
	}

	@Override
	public void destroy() {
		System.out.println("CharacterEncodingFilter销毁");
	}
}