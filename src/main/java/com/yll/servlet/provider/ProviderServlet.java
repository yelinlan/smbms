package com.yll.servlet.provider;

import cn.hutool.json.JSONUtil;
import com.yll.constant.Constant;
import com.yll.pojo.Provider;
import com.yll.pojo.User;
import com.yll.service.ProviderService;
import com.yll.service.impl.ProviderServiceImpl;
import com.yll.servlet.common.BaseServlet;
import com.yll.util.ConvertUtil;
import com.yll.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ProviderServlet extends BaseServlet {

	private ProviderService providerService;

	public ProviderServlet() {
		providerService = new ProviderServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		request = req;
		response = resp;
		String method = req.getParameter("method");
		if (method.equals("query")) {
			query("providerlist.jsp");
		} else if (method.equals("add")) {
			add("/jsp/provider.do?method=query", "provideradd.jsp");
		} else if (method.equals("view")) {
			getProviderById("providerview.jsp");
		} else if (method.equals("modify")) {
			getProviderById("providermodify.jsp");
		} else if (method.equals("modifysave")) {
			modify("/jsp/provider.do?method=query", "providermodify.jsp");
		} else if (method.equals("delprovider")) {
			delProvider();
		}
	}

	private void query(String url) {
		String queryProName = request.getParameter("queryProName");
		String queryProCode = request.getParameter("queryProCode");
		List<Provider> providerList = providerService.getProviderList(queryProName, queryProCode);
		request.setAttribute("providerList", providerList);
		request.setAttribute("queryProName", queryProName);
		request.setAttribute("queryProCode", queryProCode);
		dispatcher(url);
	}

	private void add(String url, String url1) {
		Provider provider = ConvertUtil.convertBean(Provider.class, request);
		provider.setCreatedBy(((User) request.getSession().getAttribute(Constant.USER_SESSION)).getId());
		provider.setCreationDate(LocalDateTime.now());
		boolean flag = false;
		flag = providerService.add(provider);
		if (flag) {
			redirect(url);
		} else {
			dispatcher(url1);
		}
	}

	private void delProvider() {
		String id = request.getParameter("proid");
		if (!StringUtils.isNullOrEmpty(id)) {
			int flag = providerService.deleteProviderById(id);
			if (flag == 0) {//删除成功
				resultData.setDelResult("true");
			} else if (flag == -1) {//删除失败
				resultData.setDelResult("false");
			} else if (flag > 0) {//该供应商下有订单，不能删除，返回订单数
				resultData.setDelResult(String.valueOf(flag));
			}
		} else {
			resultData.setDelResult("notexit");
		}
		result(JSONUtil.toJsonStr(resultData));
	}

	private void modify(String url, String url1) {
		Provider provider = ConvertUtil.convertBean(Provider.class, request);
		provider.setModifyBy(((User) request.getSession().getAttribute(Constant.USER_SESSION)).getId());
		provider.setModifyDate(LocalDateTime.now());
		boolean flag = false;
		flag = providerService.modify(provider);
		System.out.println("--------------------------->" + flag);
		if (flag) {
			redirect(url);
		} else {
			dispatcher(url1);
		}
	}

	private void getProviderById(String url) {
		String id = request.getParameter("proid");
		if (!StringUtils.isNullOrEmpty(id)) {
			Provider provider = null;
			provider = providerService.getProviderById(id);
			request.setAttribute("provider", provider);
			dispatcher(url);
		}
	}

}
