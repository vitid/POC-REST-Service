CREATE TABLE product (
  id INTEGER PRIMARY KEY,
  name VARCHAR(30),
  price  DECIMAL
);

CREATE TABLE user_order(
	id INTEGER PRIMARY KEY,
	product_id INTEGER,
	quantity INTEGER,
	order_time DATETIME,
	last_modified DATETIME,
	FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE ON UPDATE CASCADE
);

insert into product values(1,'productA',100);
insert into product values(2,'productB',200);
insert into product values(3,'productC',300);
insert into product values(4,'productD',400);
insert into product values(5,'productE',500);
insert into product values(6,'productF',600);
insert into product values(7,'productG',700);
insert into product values(8,'productH',800);
