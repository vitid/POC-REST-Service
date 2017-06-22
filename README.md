# POC-REST-Service
Simple Proof Of Concept Ordering RESTful API service using Java with Spring MVC and Hibernate framework

# REST APIs Provided

- List product catalog: 

    - URL: `/rest/product/list`
  
    - Return: List of products representation
  
    - Ex: `[{"id":1,"name":"productA","price":100.0},{"id":2,"name":"productB","price":200.0},...]`

- Read a single product by its id
   
    - URL: `/rest/product/get?id={product_id}`
  
    - Return: Product representation
  
    - Ex: `{"id":4,"name":"productD","price":400.0}` or ` ` if product_id not exist

- Place an order for {product_id} for {quantity} amount

    - URL: `/rest/user_order/place?product_id={product_id}&quantity={quantity}`
  
    - Return: code status - 0 (success) or 1 (fail) and a correspoinding message
  
    - Ex: `{"code":0,"msg":""}`, `{"code":1,"msg":"product_id not exist"}`, `{"code":1,"msg":"quantity is not valid, withdrawing order is not allowed"}`


- Modify an existing order {order_id} by changing order amount to {quantity}

    - URL: `/rest/user_order/update?id={order_id}&quantity={quantity}`
  
    - Return: code status - 0 (success) or 1 (fail) and a correspoinding message
  
    - Ex: `{"code":0,"msg":""}`, `{"code":1,"msg":"order_id not exist"}`, `{"code":1,"msg":"quantity is not valid, withdrawing order is not allowed"}`

- List placed orders

    - URL: `/rest/user_order/list`
  
    - Return: List of orders
  
    - Ex: `[{"id":1,"product":{"id":1,"name":"productA","price":100.0},"quantity":1010,"orderTime":1496014946976,"lastModified":1496015307624},...]`

- Read an existing order by its id

    - URL: `/rest/user_order/get?id={order_id}`
  
    - Return: Order representation
  
    - Ex: `{"id":1,"product":{"id":1,"name":"productA","price":100.0},"quantity":1010,"orderTime":1496014946976,"lastModified":1496015307624}` or ` ` if not exist

# Configuration

- To config initial product data, update insert statements in [import.sql](src/main/resources/import.sql)

# Build & Run & Test

Go to the root directory

- To build and install to your local repository, run: 

    `mvn clean install`

- To run directly from Maven(with Jetty embedded), run: 

    `mvn jetty:run`
    
    The service on your machine can be accessed by calling to `http://localhost:8080/...`

- To run the test suite, run:
  
    `mvn surefire:test`
