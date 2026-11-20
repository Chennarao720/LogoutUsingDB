package com.example.productinventory.security;

import com.example.productinventory.entity.BlacklistedToken;
import com.example.productinventory.repository.BlacklistedTokenRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogoutService {

    private final TokenHashUtil tokenHashUtil;
    private final JwtService jwtService;
    private final BlacklistedTokenRepository repository;

    public LogoutService(TokenHashUtil tokenHashUtil, JwtService jwtService, BlacklistedTokenRepository repository) {
        this.tokenHashUtil = tokenHashUtil;
        this.jwtService = jwtService;
        this.repository = repository;
    }

//    Saving the logout token in the database
    public void logout(String token){
        System.out.println("before hashing of token in service");
        String hashToken = tokenHashUtil.genrateHash(token);
        LocalDateTime expiryTime =
              jwtService.extractExpiration(token);
        System.out.println("before hashing of token in service");

        BlacklistedToken blacklistedToken=new BlacklistedToken(hashToken,expiryTime);
        repository.save(blacklistedToken);
        System.out.println("saved in database");
    }

    //check the is token is BlackListed or not
    public boolean isBlacklisted(String  token){
        String hashedToken = tokenHashUtil.genrateHash(token);
        boolean isBlackListed1 = repository.existsByTokenHash(hashedToken);
        return isBlackListed1;

    }
}
