package uz.pdp.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StatisticsDto {
    private Integer allUsers;
    private Integer deleteUsers;
    private Long allCards;
    private BigDecimal totalAmount;
    private BigDecimal totalCommission;
    private Long transactionsCount;

}
