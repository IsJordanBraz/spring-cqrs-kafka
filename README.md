# spring-cqrs-kafka
Java Microservices: CQRS &amp; Event Sourcing with Kafka

```
docker run -it -d --name mongo-container -p 27017:27017 --network spring-cqrs-kafka --restart always -v mongodb_data_container:/data/db -d mongo:latest

```

```
docker run -it -d --name mysql-container -p 3306:3306 --network spring-cqrs-kafka --restart always -v mysql_data_container:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=1L23$!@LDKAM mysql:latest

```

```
docker run -it -d --name adminer -p 8080:8080 --network spring-cqrs-kafka --restart always -e ADMINER_DEFAULT_SERVER=mysql-container adminer:latest

```