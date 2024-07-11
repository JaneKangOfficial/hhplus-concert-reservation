package io.hhplus.concert.domain.concerts.business.repository;

import io.hhplus.concert.domain.concerts.infrastructure.entity.UsersEntity;

import java.util.Optional;

public interface UsersRepository {

    Optional<UsersEntity> findByUserId(Long userId);

    void updatePointByUserId(UsersEntity usersEntity);
}
