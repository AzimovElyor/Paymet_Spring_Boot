package uz.pdp.springboot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.springboot.enums.TransactionRole;

import java.time.LocalDateTime;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserTransactions {
    private int page = 0;
    private int size = 5;
    private TransactionRole role = TransactionRole.ALL;
    private LocalDateTime startDate = LocalDateTime.now().minusDays(3);
    private LocalDateTime finishDate = LocalDateTime.now();
}
