# microservice
learning of microservices

![img.png](img.png)


--> GIT Hun Location for the code:
https://github.com/vikrant-bhati/microservice.git/




--> Currency Exchange Service
URL
http://localhost:8000/currency-exchange/from/USD/to/INR

	Ports:
	8000,8001,8002...

	H2-Databse Details:

	URL
	http://localhost:8000/h2-console


	Response Structure
	{
	   "id":10001,
	   "from":"USD",
	   "to":"INR",
	   "conversionMultiple":65.00,
	   "environment":"8000 instance-id"
	}




--> Currency Conversion Service

	URL - Rest Template
	http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10

	URL - Feign
	http://localhost:8100/currency-conversion-feign/from/USD/to/INR/quantity/10

	Ports:
	8100,8101,8102...
	
	Response Structure
	{
	  "id": 10001,
	  "from": "USD",
	  "to": "INR",
	  "conversionMultiple": 65.00,
	  "quantity": 10,
	  "totalCalculatedAmount": 650.00,
	  "environment": "8000 instance-id"
	}
	  Will be using feign to configure routing to the Currency Exchange Service
	<!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-feign -->
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-starter-feign</artifactId>
		<version>1.4.7.RELEASE</version>
	</dependency>




--> Now We also created one naming server and using Eureka server and have configured eureka discovery client in
all the server

	Naming-server URL:
	http://localhost:8761
	
	port:8761



--> We have used api-gateway to call these services we created below URLS:

	api-gateway URL:
	http://localhost:8765/*
	
	port:8765
	
	Old URL(with capital letters in URIs):
	http://localhost:8765/CURRENCY-EXCHANGE/currency-exchange/from/USD/to/INR
	http://localhost:8765/CURRENCY-CONVERSION/currency-conversion-feign/from/USD/to/INR/quantity/10
	http://localhost:8765/CURRENCY-CONVERSION/currency-conversion/from/USD/to/INR/quantity/10


	Updated URL(with small case URIs):
	http://localhost:8765/currency-exchange/currency-exchange/from/USD/to/INR
	http://localhost:8765/currency-conversion/currency-conversion-feign/from/USD/to/INR/quantity/10
	http://localhost:8765/currency-conversion/currency-conversion/from/USD/to/INR/quantity/10



--> If we notice closely we have two currency-conversion and currency-exhcnage in the URL we can fix this using filters.
New URL after using filter:
http://localhost:8765/currency-exchange/from/USD/to/INR
http://localhost:8765/currency-conversion-feign/from/USD/to/INR/quantity/10
http://localhost:8765/currency-conversion/from/USD/to/INR/quantity/10

	Just for fun I created another route for fun
	http://localhost:8765/random-url/from/USD/to/INR/quantity/10 -> redirect to http://localhost:8765/currency-conversion/from/USD/to/INR/quantity/10


--> Resilience4J circuit Breaker  https://resilience4j.readme.io/docs/getting-started-3
