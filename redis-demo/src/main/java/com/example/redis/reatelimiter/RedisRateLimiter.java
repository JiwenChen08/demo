package com.example.redis.reatelimiter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class RedisRateLimiter {

    @Autowired
    private StringRedisTemplate template;

    private final double capacity = 10.0;
    private final double refillRate = 1;

    private final String LuaScript = """
            local key = KEYS[1]
            local capacity = tonumber(ARGV[1])
            local refillRate = tonumber(ARGV[2])
            local now = tonumber(ARGV[3])
            
            local bucket = redis.call('HMGET', key, 'tokens', 'lastRefill')
            local tokens = tonumber(bucket[1]) or capacity
            local lastRefill = tonumber(bucket[2]) or now
            
            local delta = (now - lastRefill) / 1000.0 * refillRate
            tokens = math.min(capacity, tokens + delta)
            tokens = math.floor(tokens * 1000) / 1000
            
            if tokens >= 1 then
                redis.call('HMSET', key, 'tokens', tokens-1, 'lastRefill', now)
                return 1
            else
                redis.call('HMSET', key, 'tokens', tokens, 'lastRefill', now)
                return 0
            end
            
            """;

    public boolean allowRequest(String userId) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(LuaScript, Long.class);

        Long result = template.execute(
                redisScript,
                List.of("rateLimit:" + userId + ":bucket"),
                String.valueOf(capacity),
                String.valueOf(refillRate),
                String.valueOf(System.currentTimeMillis())
        );

        return Objects.equals(result, 1L);
    }


}