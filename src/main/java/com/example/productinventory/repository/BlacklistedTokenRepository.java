package com.example.productinventory.repository;

import com.example.productinventory.entity.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken,String> {
    boolean existsByTokenHash(String tokenHash);
}
