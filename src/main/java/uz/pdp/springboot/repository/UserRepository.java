package uz.pdp.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.pdp.springboot.entity.User;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByIdAndIsActiveFalse(UUID userId);
    Optional<User> findByUsername(String username);
    Page<User> findByIsActiveFalse(Pageable pageable);
    @Modifying
    @Query("UPDATE  User u set u.name=:name,u.username=:username,u.password=:password,u.updatedDate=current_timestamp  where u.id=:id")
    int updateUser(@Param("name") String name,
                   @Param("username") String username,
                   @Param("password") String password,
                   @Param("id") UUID id
    );
    @Modifying
    @Query("UPDATE User u set u.isActive = true where u.id = :userId")
    int deleteUser(@Param("userId") UUID id);
    int countAllBy();
    int countAllByIsActiveTrue();

}
