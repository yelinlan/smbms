package com.yll.filter;

import com.yll.constant.Constant;

import javax.servlet.*;
import java.io.IOException;

/**
 *@项目名称: MyServlet
 *@类名称: CharacterEncodingFilter
 *@类描述:
 *@创建人: quanyixiang
 *@创建时间: 2022/11/8 23:42
 **/
public class CharacterEncodingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("CharacterEncodingFilter初始化...");
		Filter.super.init(filterConfig);
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
		Filter.super.destroy();
	}
}