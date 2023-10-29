package com.example.superheroes.config;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.cache.CacheManager;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Configuration class for setting up a Caffeine-based Cache Manager.
 */
@Configuration
@EnableCaching
public class CacheManagerConfig {

    /**
     * Defines the cache manager that uses Caffeine as the caching provider.
     *
     * @return A Caffeine-based CacheManager.
     */
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(caffeineCacheBuilder());
        cacheManager.setCacheNames(List.of("superheroesCache"));
        return cacheManager;
    }

    /**
     * Configures the Caffeine cache builder with specific settings.
     *
     * @return A Caffeine cache builder with configured settings.
     */
    @NonNull Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(500)
                .expireAfterWrite(10, TimeUnit.MINUTES);
    }
}
