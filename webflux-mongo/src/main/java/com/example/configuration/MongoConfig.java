package com.example.configuration;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
public class MongoConfig extends AbstractReactiveMongoConfiguration {
    @Value("${spring.mongodb.uri}")
    private String mongoUri;

    @Value("${mongodb.name}")
    private String mongoName;

    @Override
    protected String getDatabaseName() {
        return mongoName;
    }

    @Override
    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(mongoUri);
    }
}
