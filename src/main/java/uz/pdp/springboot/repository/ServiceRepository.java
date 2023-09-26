package uz.pdp.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springboot.entity.ServiceEntity;

import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<ServiceEntity , UUID> {

    Optional<ServiceEntity> findByTitleAndIsActiveFalse(String title);
    Page<ServiceEntity> findAllByIsActiveFalse(Pageable pageable);
    Optional<ServiceEntity> findByIdAndIsActiveFalse(UUID id);
}
