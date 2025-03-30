package com.practica.config;

import de.flapdoodle.embed.mongo.distribution.IFeatureAwareVersion;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class MongoTestConfiguration {

    @Bean
    public IFeatureAwareVersion version() {
        return Version.Main.V6_0;
    }
}