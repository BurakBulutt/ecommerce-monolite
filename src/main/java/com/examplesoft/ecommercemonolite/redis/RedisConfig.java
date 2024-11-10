package com.examplesoft.ecommercemonolite.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
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
                .entryTtl(Duration.ofMinutes(10))
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

            var categoryCache = cacheConfiguration()
                    .entryTtl(Duration.ofMinutes(5));
            var userCache = cacheConfiguration()
                    .entryTtl(Duration.ofMinutes(10));

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
