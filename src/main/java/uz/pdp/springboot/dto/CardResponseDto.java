package uz.pdp.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CardResponseDto {
    UUID id;
    private String cardNumber;
    private UUID ownerId;
    private LocalDate validDate;
    private BigDecimal balance;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
