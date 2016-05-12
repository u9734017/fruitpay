package com.fruitpay.base.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fruitpay.base.comm.CommConst;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.CustomerOrder;
import com.fruitpay.base.model.OrderStatus;
import com.fruitpay.base.model.ShipmentChange;
import com.fruitpay.base.model.ShipmentDeliveryStatus;
import com.fruitpay.base.model.ShipmentRecord;

public interface ShipmentService {
	
	public Page<ShipmentChange> findByValidFlag(CommConst.VALID_FLAG validFlag, int page, int size);
	
	public List<ShipmentChange> findChangesByOrderId(int orderId);
	
	public List<ShipmentRecord> findRecordsByOrderId(int orderId);

	public ShipmentChange add(ShipmentChange shipmentChange);
	
	public ShipmentChange updateValidFlag(ShipmentChange shipmentChange, CommConst.VALID_FLAG validFlag);
	
	public ShipmentChange update(ShipmentChange shipmentChange);
	
	public Boolean delete(ShipmentChange shipmentChange);
	
	public List<ShipmentDeliveryStatus> getAllDeliveryStatus(Date startDate, Date endDate, int orderId);
	
	public Page<CustomerOrder> listAllOrdersByDate(LocalDate date, int page, int size);
	
	public Page<CustomerOrder> findByOrderIdIn(List<Integer> orderIds, int page, int size);
	
}
