package uz.pdp.springboot.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.springboot.enums.Category;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceRequestDto {
    @NotBlank(message = "service ni titlesi bosh yoki null bolmasligi kerak")
    private String title;
    @NotNull(message = "Category null bolmasligi kerak")
    private Category category ;
    @DecimalMin(value = "0.0", message = "Commission manfiy bolmasligi kerak")
    private BigDecimal commission = BigDecimal.ZERO;
}
