package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity, Long> {

    Optional<WalletEntity> findByUuid(String uuid);

}
