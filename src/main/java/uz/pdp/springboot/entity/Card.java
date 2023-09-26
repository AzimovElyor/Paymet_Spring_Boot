package uz.pdp.springboot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.Check;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Card extends BaseEntity{
    private String cardNumber;
    @ManyToOne
    private User owner;
    private LocalDate validDate;
    @Check(name = "card_balance_check", constraints = "balance >= 0")
    private BigDecimal balance;
    private boolean isActive;
    private String password;

}
