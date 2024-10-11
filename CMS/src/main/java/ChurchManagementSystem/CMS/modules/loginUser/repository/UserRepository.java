package ChurchManagementSystem.CMS.modules.loginUser.repository;

import ChurchManagementSystem.CMS.modules.loginUser.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>, JpaSpecificationExecutor<UserEntity> {
    UserEntity findByEmail(String email);

    UserEntity findByUsername(String userName);

    List<UserEntity> findAllByOrderByRegDateTimeDesc();

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
