package com.yll.servlet.bill;


import cn.hutool.json.JSONUtil;
import com.yll.constant.Constant;
import com.yll.pojo.Bill;
import com.yll.pojo.Provider;
import com.yll.pojo.User;
import com.yll.service.BillService;
import com.yll.service.ProviderService;
import com.yll.service.impl.BillServiceImpl;
import com.yll.service.impl.ProviderServiceImpl;
import com.yll.servlet.common.BaseServlet;
import com.yll.util.ConvertUtil;
import com.yll.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class BillServlet extends BaseServlet {

	private BillService billService;
	private ProviderService providerService;

	public BillServlet() {
		this.providerService = new ProviderServiceImpl();
		this.billService = new BillServiceImpl();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		request = req;
		response = resp;
		String method = req.getParameter("method");
		if (method != null && method.equals("query")) {
			query("billlist.jsp");
		} else if (method != null && method.equals("add")) {
			add("/jsp/bill.do?method=query", "billadd.jsp");
		} else if (method != null && method.equals("view")) {
			getBillById("billview.jsp");
		} else if (method != null && method.equals("modify")) {
			getBillById("billmodify.jsp");
		} else if (method != null && method.equals("modifysave")) {
			modify("/jsp/bill.do?method=query", "billmodify.jsp");
		} else if (method != null && method.equals("delbill")) {
			delBill();
		} else if (method != null && method.equals("getproviderlist")) {
			getProviderlist();
		}
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

	private void query(String url) {
		String queryProductName = Objects.toString(request.getParameter("queryProductName"), "");
		String queryProviderId = request.getParameter("queryProviderId");
		String queryIsPayment = request.getParameter("queryIsPayment");
		Bill bill = new Bill();
		bill.setIsPayment(StringUtils.isNullOrEmpty(queryIsPayment) ? 0 : Integer.parseInt(queryIsPayment));
		bill.setProviderId(StringUtils.isNullOrEmpty(queryProviderId) ? 0 : Integer.parseInt(queryProviderId));
		bill.setProductName(queryProductName);

		List<Provider> providerList = providerService.getProviderList("", "");
		List<Bill> billList = billService.getBillList(bill);

		request.setAttribute("providerList", providerList);
		request.setAttribute("billList", billList);
		request.setAttribute("queryProductName", queryProductName);
		request.setAttribute("queryProviderId", queryProviderId);
		request.setAttribute("queryIsPayment", queryIsPayment);
		dispatcher(url);

	}

	private void add(String url, String url1) {
		Bill bill = ConvertUtil.convertBean(Bill.class, request);
		bill.setProductCount(new BigDecimal(String.valueOf(bill.getProductCount())).setScale(2, BigDecimal.ROUND_DOWN));
		bill.setTotalPrice(new BigDecimal(String.valueOf(bill.getTotalPrice())).setScale(2, BigDecimal.ROUND_DOWN));
		bill.setCreatedBy(((User) request.getSession().getAttribute(Constant.USER_SESSION)).getId());
		bill.setCreationDate(LocalDateTime.now());
		boolean flag = false;
		flag = billService.add(bill);
		if (flag) {
			redirect(url);
		} else {
			dispatcher(url1);
		}
	}

	private void getBillById(String url) {
		String id = request.getParameter("billid");
		if (!StringUtils.isNullOrEmpty(id)) {
			Bill bill = billService.getBillById(id);
			request.setAttribute("bill", bill);
			dispatcher(url);
		}
	}

	private void modify(String url, String url1) {
		Bill bill = ConvertUtil.convertBean(Bill.class, request);
		bill.setModifyBy(((User) request.getSession().getAttribute(Constant.USER_SESSION)).getId());
		bill.setModifyDate(LocalDateTime.now());
		boolean flag = false;
		flag = billService.modify(bill);
		if (flag) {
			redirect(url);
		} else {
			dispatcher(url1);
		}
	}

	private void delBill() {
		String id = request.getParameter("billid");
		if (!StringUtils.isNullOrEmpty(id)) {
			boolean flag = billService.deleteBillById(id);
			if (flag) {//删除成功
				resultData.setDelResult("true");
			} else {//删除失败
				resultData.setDelResult("false");
			}
		} else {
			resultData.setDelResult("notexit");
		}
		result(JSONUtil.toJsonStr(resultData));
	}

	private void getProviderlist() {
		List<Provider> providerList = providerService.getProviderList("", "");
		result(JSONUtil.toJsonStr(providerList));
	}
}