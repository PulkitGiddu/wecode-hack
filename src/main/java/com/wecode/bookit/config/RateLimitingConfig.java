package com.wecode.bookit.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class RateLimitingConfig {

    // Store buckets per IP address
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    /**
     * here we can customize this token
     * Create a bucket with 50 requests per hour
     * Refill rate: 50 tokens per hour
     */
    @Bean
    public Bucket createBucket() {
        Bandwidth limit = Bandwidth.classic(10, Refill.intervally(10, Duration.ofHours(1)));
        return Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    /**
     * Get or create a bucket for a specific IP address
     * Provides per-IP rate limiting
     */
    public Bucket resolveBucket(String key) {
        return buckets.computeIfAbsent(key, k -> createBucket());
    }

    /**
     * Get all active buckets
     */
    public Map<String, Bucket> getAllBuckets() {
        return buckets;
    }

    /**
     * Clear specific bucket (useful for admin operations)
     */
    public void clearBucket(String key) {
        buckets.remove(key);
    }

    /**
     * Clear all buckets (for reset operations)
     */
    public void clearAllBuckets() {
        buckets.clear();
    }
}

