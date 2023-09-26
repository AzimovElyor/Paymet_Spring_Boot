package uz.pdp.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto {
    @NotBlank(message = "Username null yoki bosh bolmasligi kerak")
    private String username;
    @NotBlank(message = "Password null yoki bosh bolmasligi" )
    private String password;
}
