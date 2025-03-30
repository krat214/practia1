package com.practica.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import org.springframework.test.context.ActiveProfiles;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.NoOpDbRefResolver;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

@TestConfiguration
@ActiveProfiles("test")
public class TestMongoConfig {

    @Bean
    public MongoClient reactiveMongoClient() {
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate(MongoClient reactiveMongoClient) {
        ReactiveMongoDatabaseFactory factory = new SimpleReactiveMongoDatabaseFactory(reactiveMongoClient, "test");

        MappingMongoConverter converter = new MappingMongoConverter(
                NoOpDbRefResolver.INSTANCE,
                new MongoMappingContext());

        return new ReactiveMongoTemplate(factory, converter);
    }
}