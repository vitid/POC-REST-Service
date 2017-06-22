package com.server;
import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

import com.server.db.dao.Product;
import com.server.db.dao.UserOrder;
import com.github.springtestdbunit.*;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml",
		"classpath:testDataSourceContext.xml",
		"classpath:rest-servlet.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class,
	DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class RestControllerTest{
	@Autowired
	private RestController restController;
	@Autowired
	private DateManager dateManager;
	Calendar c;
	
	public RestControllerTest(){
		c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND,0);
		c.set(Calendar.MILLISECOND,0);
		c.set(Calendar.DAY_OF_MONTH,1);
		c.set(Calendar.MONTH,0);
	}
	
	@Before
	public void setUp(){
		restController.setDateManager(dateManager);
	}
	
	@Test
	@DatabaseSetup("/dbunit/db_full.xml")
	public void testGetProduct() {
		Product product = restController.getProduct(1);
		assertEquals(1, product.getId());
		assertEquals("mockA", product.getName());
		assertEquals(100, product.getPrice(), 1);
		
		assertNull(restController.getProduct(6));
	}

	@Test
	@DatabaseSetup("/dbunit/db_empty.xml")
	public void testListProductEmpty() {
		assertEquals(0, restController.listProduct().size());
	}
	
	@Test
	@DatabaseSetup("/dbunit/db_full.xml")
	public void testListProductFull() {
		assertEquals(5, restController.listProduct().size());
	}

	@Test
	@DatabaseSetup("/dbunit/db_full.xml")
	@ExpectedDatabase("/dbunit/db_full.xml")
	public void testPlaceOrderInValid() {
		ResponseStatus response = restController.placeOrder(6, 300);
		assertEquals(new ResponseStatus("product_id not exist"), response);
		
		response = restController.placeOrder(1, 0);
		assertEquals(new ResponseStatus("quantity is not valid, withdrawing order is not allowed"), response);
		
		response = restController.placeOrder(1, -1);
		assertEquals(new ResponseStatus("quantity is not valid, withdrawing order is not allowed"), response);
	}
	
	@Test
	@DatabaseSetup("/dbunit/db_full.xml")
	@ExpectedDatabase("/dbunit/db_full2.xml")
	public void testPlaceOrderValid() {
		c.set(Calendar.YEAR,2020);
		DateManager mock = PowerMockito.mock(DateManager.class);
		PowerMockito.when(mock.getCurrentTime()).thenReturn(c.getTime());
		restController.setDateManager(mock);
		
		restController.placeOrder(3, 300);
		restController.placeOrder(4, 400);
		restController.placeOrder(5, 500);
		
	}

	@Test
	@DatabaseSetup("/dbunit/db_full.xml")
	@ExpectedDatabase("/dbunit/db_full.xml")
	public void testUpdateOrderInvalid() {
		ResponseStatus response = restController.updateOrder(6, 100);
		assertEquals(new ResponseStatus("order_id not exist"), response);
		
		response = restController.updateOrder(1, 0);
		assertEquals(new ResponseStatus("quantity is not valid, withdrawing order is not allowed"), response);
		
		response = restController.updateOrder(1, -1);
		assertEquals(new ResponseStatus("quantity is not valid, withdrawing order is not allowed"), response);
	}
	
	@Test
	@DatabaseSetup("/dbunit/db_full.xml")
	@ExpectedDatabase("/dbunit/db_full_updated.xml")
	public void testUpdateOrderValid() {
		c.set(Calendar.YEAR,2021);
		DateManager mock = PowerMockito.mock(DateManager.class);
		PowerMockito.when(mock.getCurrentTime()).thenReturn(c.getTime());
		restController.setDateManager(mock);
		
		restController.updateOrder(5, 5000);
	}

	@Test
	@DatabaseSetup("/dbunit/db_full.xml")
	public void testGetOrder() {
		
		UserOrder order = restController.getOrder(3);
		assertEquals(3, order.getId());
		assertEquals(2, order.getProduct().getId());
		assertEquals(300, order.getQuantity());
		c.set(Calendar.YEAR,2010);
		assertEquals(c.getTime(), order.getOrderTime());
		c.set(Calendar.YEAR,2012);
		assertEquals(c.getTime(), order.getLastModified());
		
		order = restController.getOrder(6);
		assertNull(order);
	}
	
	@Test
	@DatabaseSetup("/dbunit/db_empty.xml")
	public void testListOrderEmpty() {
		assertEquals(0, restController.listOrder().size());	
	}

	@Test
	@DatabaseSetup("/dbunit/db_full.xml")
	public void testListOrderFull() {
		assertEquals(5, restController.listOrder().size());	
	}

}
