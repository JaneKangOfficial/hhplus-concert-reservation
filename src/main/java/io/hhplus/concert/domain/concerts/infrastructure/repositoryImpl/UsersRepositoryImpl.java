package io.hhplus.concert.domain.concerts.infrastructure.repositoryImpl;

import io.hhplus.concert.domain.concerts.business.repository.UsersRepository;
import io.hhplus.concert.domain.concerts.infrastructure.entity.UsersEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor // 생성자 자동 생성
public class UsersRepositoryImpl implements UsersRepository {

    private final UsersJpaRepository usersJpaRepository;

    @Override
    public Optional<UsersEntity> findByUserId(Long userId) {
        return usersJpaRepository.findByUserId(userId);
    }

    @Override
    public void updatePointByUserId(UsersEntity usersEntity) {
        usersJpaRepository.updatePointByUserId(usersEntity.getUserId(), usersEntity.getPoint());
    }


}
