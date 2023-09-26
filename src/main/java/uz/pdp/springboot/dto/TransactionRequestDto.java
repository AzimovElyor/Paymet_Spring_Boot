package uz.pdp.springboot.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionRequestDto {
    @NotNull(message = "Owner id null bolmasligi kerak")
    private UUID ownerId;
    @NotBlank(message = "sender Card numberi null yoki bosh  bolmasligi kerak")
    @Size(min = 8,max = 8, message = "Karta raqamini uzunligi {min} bolishi kerak")
    private String senderCardNumber;
    @NotBlank(message = "Receiver Card numberi null yoki bosh  bolmasligi kerak")
    @Size(min = 8,max = 8, message = "Karta raqamini uzunligi {min} bolishi kerak")
    private String receiverCardNumber;
    @DecimalMin(value = "1000" , message = "Pul otkazmalari kamida {value} som bolishi kerak")
    private BigDecimal price;
    @NotNull(message = "IsIn null bolmasligi kerak")
    private Boolean isIn;
}
