package com.example.productinventory.schedularJobs;

import com.example.productinventory.security.TokenStore;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCleanupScheduler {

@Scheduled(fixedRate = 60000)
public void removeExpiredTokens(){
    TokenStore.removeTokens();
}
}
