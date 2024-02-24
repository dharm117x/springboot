Steps to start kafak server:
D:\Apps\kafka_2\bin\windows>

1. Start zookeeper:
zookeeper-server-start.bat  ..\..\config\zookeeper.properties

2. Start kafak server:
kafka-server-start.bat ..\..\config\server.properties

Topic:
kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test

Producer:
kafka-console-producer.bat --broker-list localhost:9092 --topic test

Consumer:
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning