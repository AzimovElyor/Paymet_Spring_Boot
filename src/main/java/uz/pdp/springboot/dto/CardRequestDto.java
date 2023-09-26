package uz.pdp.springboot.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CardRequestDto {
@Size(min = 8,max = 8, message = "Karta raqamini uzunligi {min} bolishi kerak")
@Pattern(regexp = "^[0-9]{8}" , message = "Karta raqami  8 ta raqamdan iborat bolishi kerak")
    private String cardNumber;
    @NotNull(message = "kartani egasini id si null bolmasligi kerak")
    private UUID ownerId;
    @Future(message = "validDate hozirgi vaqtdan keyin bolishi kerak")
    private LocalDate validDate;
    @DecimalMin(value = "0.0", message = "Kartan balance manfiy son bolmasligi kerak")
    private BigDecimal balance;
    @NotBlank(message = "Password bosh bolmaslgi kerak")
    @Size(min = 4, max = 4 , message = "Password {min} ta uzunlikda bolishi kerak")
    @Pattern(regexp = "^[0-9]{4}$", message = "Carta paroli faqat 4 ta raqamdan iborat bolishi kerak")
    private String password;
}
