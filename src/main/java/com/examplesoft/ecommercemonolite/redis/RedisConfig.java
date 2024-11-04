package com.examplesoft.ecommercemonolite.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        return RedisCacheConfiguration
                .defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(30))
                .disableCachingNullValues()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair
                        .fromSerializer(serializer()));
    }

    @Bean
    public RedisCacheManagerBuilderCustomizer redisCacheManagerBuilderCustomizer() {
        return builder -> {
            builder.cacheDefaults(cacheConfiguration());
            builder.transactionAware();

            var categoryCache = RedisCacheConfiguration
                    .defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(1))
                    .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer()));
            var userCache = RedisCacheConfiguration
                    .defaultCacheConfig()
                    .entryTtl(Duration.ofMinutes(1))
                    .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                    .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer()));

            builder.withCacheConfiguration("categoryCache", categoryCache);
            builder.withCacheConfiguration("userCache", userCache);
        };
    }

    @Bean
    public GenericJackson2JsonRedisSerializer serializer() {
        ObjectMapper objectMapper = new ObjectMapper();
        return GenericJackson2JsonRedisSerializer.builder()
                .defaultTyping(true)
                .objectMapper(objectMapper)
                .build();
    }

}
