package com.fruitpay.base.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.comm.returndata.ReturnMessageEnum;
import com.fruitpay.base.dao.CouponDAO;
import com.fruitpay.base.model.Coupon;
import com.fruitpay.base.service.CouponService;

@Service
public class CouponServiceImpl implements CouponService {

	@Inject
	private CouponDAO couponDAO;

	@Override
	public List<Coupon> findAll() {
		List<Coupon> coupons = couponDAO.findAll();
		return coupons;
	}

	@Override
	public Coupon findById(Integer couponId) {
		Coupon coupon = couponDAO.findOne(couponId);
		return coupon;
	}

	@Override
	@Transactional
	public Coupon add(Coupon coupon) {
		coupon = couponDAO.save(coupon);
		return coupon;
	}

	@Override
	@Transactional
	public Coupon update(Coupon coupon) {
		Coupon origin = couponDAO.findOne(coupon.getCouponId());
		
		if(origin == null)
			throw new HttpServiceException(ReturnMessageEnum.Coupon.CouponNotFound.getReturnMessage());
		
		BeanUtils.copyProperties(coupon, origin);
		
		return origin;
	}

	@Override
	@Transactional
	public void delete(Coupon coupon) {
		couponDAO.delete(coupon);
	}
	

}