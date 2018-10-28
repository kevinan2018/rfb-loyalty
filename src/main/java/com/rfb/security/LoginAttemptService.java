package com.rfb.security;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LoginAttemptService {
    private final int MAX_ATTEMPT = 3;
    //String: IP address, Integer: attempts
    private LoadingCache<String, Integer> attempsCache;

    public LoginAttemptService() {
        super();
        attempsCache = CacheBuilder.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Integer>() {
                @Override
                public Integer load(String s) throws Exception {
                    return 0;
                }
            });
    }

    public void loginSucceeded(String key){
        attempsCache.invalidate(key);
    }

    public void loginFailed(String key) {
        int attempts = 0;
        try {
            attempts = attempsCache.get(key);
        } catch(ExecutionException e) {
            attempts = 0;
        }
        attempts++;
        attempsCache.put(key, attempts);
    }

    public boolean isBlocked(String key) {
        try{
            return attempsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            return false;
        }
    }

}
