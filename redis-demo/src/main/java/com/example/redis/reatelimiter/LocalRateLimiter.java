package com.example.redis.reatelimiter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;



@Component
public class LocalRateLimiter {

    private static class TokenBucket {

        private final double capacity;
        private final double refillRate;
        private double tokens;
        private long lastRefillTimestamp;

        public TokenBucket(double capacity, double refillRate) {
            this.capacity = capacity;
            this.refillRate = refillRate;

            this.tokens = capacity;
            this.lastRefillTimestamp = System.currentTimeMillis();
        }

        public synchronized boolean allowRequest(int cost) {
            refill();
            if (tokens >= cost) {
                tokens -= cost;
                return true;
            }
            return false;
        }

        private void refill() {

            long now = System.currentTimeMillis();

            double delta = (now - lastRefillTimestamp) / 1000.0 * refillRate;
            tokens = Math.min(tokens + delta, capacity);

            lastRefillTimestamp = now;
        }

    }

    Map<String, TokenBucket> userBuckets = new ConcurrentHashMap<>();

    public boolean allowRequest(String userId) {

        TokenBucket bucket = userBuckets.computeIfAbsent(userId,
                key -> new TokenBucket(100, 1));

        return bucket.allowRequest(1);
    }
}
