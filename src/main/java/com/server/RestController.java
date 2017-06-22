package com.server;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.server.db.dao.UserOrder;
import com.server.db.dao.UserOrderDAO;
import com.server.db.dao.Product;
import com.server.db.dao.ProductDAO;

@org.springframework.web.bind.annotation.RestController
public class RestController {
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private UserOrderDAO userOrderDAO;
	@Autowired
	private DateManager dateManager;
	
	@ResponseBody
	@RequestMapping(value="/product/get")
	public Product getProduct(@RequestParam("id") int id){
		Product product = productDAO.get(id);
		return product;
	}
	
	@ResponseBody
	@RequestMapping(value="/product/list")
	public List<Product> listProduct(){
		List<Product> l = productDAO.list();	
		
		return l;
	}

	@ResponseBody
	@RequestMapping(value="/user_order/place")
	public ResponseStatus placeOrder(@RequestParam("product_id") int productId, @RequestParam("quantity") int quantity){
		if(quantity <= 0){
			return new ResponseStatus("quantity is not valid, withdrawing order is not allowed");
		}
		
		Product product = productDAO.get(productId);
		
		if(product == null){
			return new ResponseStatus("product_id not exist");
		}
		
		Date currentTime = dateManager.getCurrentTime();
		userOrderDAO.save(new UserOrder(product, quantity, currentTime, currentTime));
		
		return new ResponseStatus();
	}
	
	@ResponseBody
	@RequestMapping(value="/user_order/update")
	public ResponseStatus updateOrder(@RequestParam("id") int id, @RequestParam("quantity") int quantity){
		if(quantity <= 0){
			return new ResponseStatus("quantity is not valid, withdrawing order is not allowed");
		}
		
		UserOrder order = userOrderDAO.get(id);
		
		if(order == null){
			return new ResponseStatus("order_id not exist");
		}
		
		Date currentTime = dateManager.getCurrentTime();
		order.setQuantity(quantity);
		order.setLastModified(currentTime);
		userOrderDAO.update(order);
		
		return new ResponseStatus();
	}
	
	@ResponseBody
	@RequestMapping(value="/user_order/get")
	public UserOrder getOrder(@RequestParam("id") int id){
		UserOrder order = userOrderDAO.get(id);
		
		return order;
	}
	
	@ResponseBody
	@RequestMapping(value="/user_order/list")
	public List<UserOrder> listOrder(){
		List<UserOrder> l = userOrderDAO.list();	
		
		return l;
	}

	//testing purpose
	public void setDateManager(DateManager dateManager) {
		this.dateManager = dateManager;
	}
	
	
}
