package com.fruitpay.base.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.service.CustomerOrderService;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.comm.model.ReturnData;
import com.fruitpay.comm.utils.AssertUtils;
import com.fruitpay.comm.utils.AuthenticationUtil;

@Controller
@RequestMapping("customerDataCtrl")
public class CustomerDataController {
	
	@Inject
	CustomerOrderService customerOrderService;
	@Inject
	CustomerService customerService;
	

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public @ResponseBody ReturnData<Customer> update(@RequestBody Customer customer,
			HttpServletRequest request, HttpServletResponse response){
		
		if(AssertUtils.isEmpty(customer) || 
				AssertUtils.isEmpty(customer.getCustomerId()))
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		ReturnData<Customer> returnData = customerService.update(customer);
		if(returnData.getObject() != null)
			AuthenticationUtil.setSessionCustomer(request, returnData.getObject());
		return returnData;
	}
	
	@RequestMapping(value = "/{customerId}/getOrder", method = RequestMethod.GET)
	public @ResponseBody ReturnData getOrder(
			@PathVariable int customerId, 
			HttpServletRequest request, HttpServletResponse response){
		if(AssertUtils.isEmpty(customerId))
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		
		Customer customer = AuthenticationUtil.getSessionCustomer(request, customerId);
		if(customer == null)
			return ReturnMessageEnum.Common.AuthenticationFailed.getReturnMessage();
		
		ReturnData<List<CustomerOrder>> customerOrders = 
				customerOrderService.getCustomerOrdersByCustomerId(customerId);
		
		return customerOrders;
	}
	
	@RequestMapping(value = "/isEmailExisted/{email}", method = RequestMethod.GET)
	public @ResponseBody ReturnData<Boolean> isEmailExisted(@PathVariable String email){
		if(AssertUtils.anyIsEmpty(email))
			return ReturnMessageEnum.Common.RequiredFieldsIsEmpty.getReturnMessage();
		
		return customerService.isEmailExisted(email.trim());
	}
	
}
