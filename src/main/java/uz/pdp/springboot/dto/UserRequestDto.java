package uz.pdp.springboot.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.springboot.enums.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
  @NotBlank(message = "Username bosh yoki null bolmasligi kerak")
  private String username;
  @NotBlank(message = "name bosh bolmasligi kerak")
  private String name;
  @Min(value = 16 , message = "Yosh {value} dan katta yoki teng bolishi kerak")
  private Integer age;
  private UserRole userRole;
  @NotBlank(message = "Password null bolmasligi yoki bosh bolmasligi kerak")
  @Size(min = 8,max = 16 , message = "Password kamida {min} ta maximum {max} ta belgidan iborat bolishi kerak")
  @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "Password kamida bitta katta harf va bitta kichkina harf va(#?!@$%^&*-) bu belgilardan iborat bolishi kerak")
  private String password;
}
