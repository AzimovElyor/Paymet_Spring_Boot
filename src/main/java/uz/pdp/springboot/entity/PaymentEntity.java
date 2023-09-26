package uz.pdp.springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity(name = "payment")
public class PaymentEntity extends BaseEntity{
    @ManyToOne
    private User owner;
    @ManyToOne
    private Card senderCard;
    @ManyToOne
    private ServiceEntity service;
    private String field;
    private BigDecimal amount;
    private BigDecimal commission;
}
