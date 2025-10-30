package ChurchManagementSystem.CMS.modules.authentication.repository;

import ChurchManagementSystem.CMS.modules.authentication.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface User extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByVerificationToken(String token);
    Optional<UserEntity> findByResetToken(String token);
}
