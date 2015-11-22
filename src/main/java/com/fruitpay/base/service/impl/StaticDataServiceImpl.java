package com.fruitpay.base.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fruitpay.base.dao.ConstantDAO;
import com.fruitpay.base.dao.OrderPlatformDAO;
import com.fruitpay.base.dao.OrderProgramDAO;
import com.fruitpay.base.dao.OrderStatusDAO;
import com.fruitpay.base.dao.PaymentModeDAO;
import com.fruitpay.base.dao.ProductDAO;
import com.fruitpay.base.dao.ShipmentPeriodDAO;
import com.fruitpay.base.dao.VillageDAO;
import com.fruitpay.base.model.Constant;
import com.fruitpay.base.model.OrderPlatform;
import com.fruitpay.base.model.OrderProgram;
import com.fruitpay.base.model.OrderStatus;
import com.fruitpay.base.model.PaymentMode;
import com.fruitpay.base.model.Product;
import com.fruitpay.base.model.ShipmentPeriod;
import com.fruitpay.base.model.Village;
import com.fruitpay.comm.model.SelectOption;

@Service
public class StaticDataServiceImpl implements com.fruitpay.base.service.StaticDataService {

	@Autowired
	VillageDAO villageDAO;
	@Autowired
	ProductDAO productDAO;
	@Autowired
	OrderPlatformDAO orderPlatformDAO;
	@Autowired
	OrderProgramDAO orderProgramDAO;
	@Autowired
	OrderStatusDAO orderStatusDAO;
	@Autowired
	PaymentModeDAO paymentModeDAO;
	@Autowired
	ShipmentPeriodDAO shipmentPeriodDAO;
	@Autowired
	ConstantDAO constantDAO;
	
	List<Constant> consantList = null;
	List<Village> allVillages = null;
	List<SelectOption> countList = null;
	Map<String, List<SelectOption>> towershipMap = null;
	Map<String, List<SelectOption>> villageMap = null;
	
	@PostConstruct
	public void init(){
		allVillages = getAllVillages();
		allVillages = removeOffIslands(allVillages);
		towershipMap = new HashMap<>();
		villageMap = new HashMap<>();
	}
	
	@Override
	public List<Village> getAllVillages() {
		return villageDAO.listAll();
	}
	
	@Override
	public List<SelectOption> getAllCounties() {
		if(countList == null){
			countList = new ArrayList<SelectOption>();
			for (Iterator<Village> iterator = allVillages.iterator(); iterator.hasNext();) {
				Village village = iterator.next();
				countList.add(new SelectOption(village.getCountyCode(), village.getCountyName()));
			}
			countList = getUnreapetedOptions(countList);
			countList = getSortedOptions(countList);
		}
		
		return countList;
	}

	@Override
	public List<SelectOption> getTowerships(String countyCode) {
		if(!towershipMap.containsKey(countyCode)){
			List<SelectOption> towershipList = new ArrayList<>();
			for (Iterator<Village> iterator = allVillages.iterator(); iterator.hasNext();) {
				Village village = iterator.next();
				if(countyCode.equals(String.valueOf(village.getCountyCode())))
					towershipList.add(new SelectOption(village.getTowershipCode(), village.getTowershipName()));			
			}
			
			if(towershipList.size() > 0){
				towershipList = getUnreapetedOptions(towershipList);
				towershipList = getSortedOptions(towershipList);
				towershipMap.put(countyCode, towershipList);
			}else{
				return null;
			} 
		}
		
		return towershipMap.get(countyCode);
	}

	@Override
	public List<SelectOption> getVillages(String towershipCode) {	
		if(!villageMap.containsKey(towershipCode)){
			List<SelectOption> villageList = new ArrayList<>();
			for (Iterator<Village> iterator = allVillages.iterator(); iterator.hasNext();) {
				Village village = iterator.next();
				if(towershipCode.equals(String.valueOf(village.getTowershipCode())))
					villageList.add(new SelectOption(village.getVillageCode(), village.getVillageName()));			
			}
			
			if(villageList.size() > 0){
				villageList = getUnreapetedOptions(villageList);
				villageList = getSortedOptions(villageList);
				villageMap.put(towershipCode, villageList);
			}else{
				return null;
			} 
		}
		
		return villageMap.get(towershipCode);
	}
	
	private List<Village> removeOffIslands(List<Village> allVillages){
		allVillages.removeIf(p -> isOffIslands(p.getCountyCode()));
		return allVillages;
	}
	
	private boolean isOffIslands(Integer countyCode){
		return countyCode == 9007 || countyCode == 9020 || countyCode == 10016;
	}

	
	private List<SelectOption> getSortedOptions(List<SelectOption> optionList){
		Collections.sort(optionList, (p1, p2) -> p1.getId().compareTo(p2.getId()));
		return optionList;
	}
	
	private List<SelectOption> getUnreapetedOptions(List<SelectOption> optionList){
		return optionList.parallelStream()
				.distinct()
				.collect(Collectors.toList());
	}

	@Override
	public List<Product> getAllProducts() {
		return productDAO.listAll();
	}

	@Override
	public List<OrderPlatform> getAllOrderPlatform() {
		return orderPlatformDAO.listAll();
	}

	@Override
	public OrderPlatform getOrderPlatform(Integer platformId) {
		return orderPlatformDAO.findById(platformId);
	}

	@Override
	public List<OrderProgram> getAllOrderProgram() {
		return orderProgramDAO.listAll();
	}

	@Override
	public OrderProgram getOrderProgram(Integer programId) {
		return orderProgramDAO.findById(programId);
	}

	@Override
	public List<OrderStatus> getAllOrderStatus() {
		return orderStatusDAO.listAll();
	}

	@Override
	public OrderStatus getOrderStatus(Integer orderStatusId) {
		return orderStatusDAO.findById(orderStatusId);
	}

	@Override
	public List<PaymentMode> getAllPaymentMode() {
		return paymentModeDAO.listAll();
	}

	@Override
	public PaymentMode getPaymentMode(Integer paymentModeId) {
		return paymentModeDAO.findById(paymentModeId);
	}

	@Override
	public List<ShipmentPeriod> getAllShipmentPeriod() {
		return shipmentPeriodDAO.listAll();
	}

	@Override
	public ShipmentPeriod getShipmentPeriod(Integer periodId) {
		return shipmentPeriodDAO.findById(periodId);
	}

	@Override
	public List<Constant> getAllConstants() {
		if(consantList == null){
			consantList = getAllConstants();
		}
		return constantDAO.listAll();
	}

	@Override
	public Constant getConstant(Integer constId) {
		return constantDAO.findById(constId);
	}
	
}
