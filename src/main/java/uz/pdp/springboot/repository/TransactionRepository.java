package uz.pdp.springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.springboot.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    Page<Transaction> findAllBySenderCardOwnerIdAndCreatedDateBetween
            (UUID ownerId, Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
    Page<Transaction> findAllBySenderCardOwnerIdOrReceiverCardOwnerIdAndCreatedDateBetween
            (UUID senderId,UUID receiverId, Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
    Page<Transaction> findAllByReceiverCardOwnerIdAndCreatedDateBetween
            (UUID receiver,Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
    Page<Transaction> getAllByCreatedDateBetween(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);
   @Query("SELECT sum(t.balance) from Transaction t")
    BigDecimal sumBalance();
   @Query("SELECT sum(t.commission) from Transaction t")
    BigDecimal sumCommission();
}
