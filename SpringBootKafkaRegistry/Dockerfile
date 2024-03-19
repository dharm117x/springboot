FROM openjdk:11
VOLUME /tmp
ADD SpringBootKafkaRegistry.jar SpringBootKafkaRegistry.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/SpringBootKafkaRegistry.jar"]