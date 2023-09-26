package uz.pdp.springboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.Check;
import uz.pdp.springboot.enums.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Transaction extends BaseEntity{
    @ManyToOne
     private User owner;
    @JoinColumn(name = "sender_id",nullable = false)
    @ManyToOne
    private Card senderCard;
    @JoinColumn(name = "receiver_id",nullable = false)
    @ManyToOne
    private Card receiverCard;
    @Check(name = "balance_check",constraints = "balance >= 0")
    private BigDecimal balance;
    private BigDecimal commission;
}

