package com.server.db.dao;

import java.util.Date;

public class UserOrder {
	private int id;
	private Product product;
	private int quantity;
	private Date orderTime;
	private Date lastModified;
	
	public UserOrder(){
		
	}

	public UserOrder(Product product, int quantity, Date orderTime, Date lastModified) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.orderTime = orderTime;
		this.lastModified = lastModified;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	
	
	
}
