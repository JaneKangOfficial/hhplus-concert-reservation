package io.hhplus.concert.domain.concerts.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.concerts.infrastructure.entity.UsersEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UsersJpaRepository extends JpaRepository<UsersEntity, Long> {

    // 비관적 락
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    Optional<UsersEntity> findByUserId(Long userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE USER_INFO SET POINT = :point WHERE USER_ID = :userId", nativeQuery = true)
    void updatePointByUserId(Long userId, Long point);

}
