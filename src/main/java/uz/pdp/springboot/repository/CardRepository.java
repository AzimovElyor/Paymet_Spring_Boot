package uz.pdp.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.pdp.springboot.entity.Card;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    Page<Card> findAllByIsActiveFalseAndOwnerId(UUID userId, Pageable pageable);
    Optional<Card> findByCardNumber(String cardNumber);
    Page<Card> findAllByIsActiveFalse(Pageable pageable);
    @Modifying
    @Query("UPDATE Card c set c.isActive = true where c.id = :cardId")
    int deleteCard(@Param("cardId") UUID id);
    Optional<Card> findByIdAndIsActiveFalse(UUID cardId);

}
