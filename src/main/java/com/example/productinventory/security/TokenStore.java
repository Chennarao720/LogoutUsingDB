package com.example.productinventory.security;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

public class TokenStore {
    private static final ConcurrentHashMap<String, Instant>
            blacklistToken=new ConcurrentHashMap<>();

    // Add token to blacklist
    public static void addToken(String token,Instant expireTime){
        blacklistToken.put(token,expireTime);
    }

    // Check whether token exists in blacklist
    public static boolean isBlacklisted(String token){
        return blacklistToken.containsKey(token);
    }

    // Remove expired tokens
    public static void removeTokens(){
        Instant now=Instant.now();
        blacklistToken.entrySet().removeIf(
                entry->!entry.getValue().isAfter(now)
        );
    }
}
