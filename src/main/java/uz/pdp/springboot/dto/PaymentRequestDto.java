package uz.pdp.springboot.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentRequestDto {
    @NotNull(message = "owner Id null bolmasligi kerak")
    private UUID ownerId;
    @NotNull(message = "sender Card id null bolmasligi kerak")
    private UUID senderCardId;
    @NotNull(message = "service entity id null bolmasligi kerak")
    private UUID serviceEntityId;
    @DecimalMin(value = "1000.0", message = "amout kamida {value} so'm bolishi kerak")
    private BigDecimal amount;
    @NotNull
    private Map<String , String> field;
}
