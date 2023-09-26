package uz.pdp.springboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.hibernate.annotations.Check;
import uz.pdp.springboot.enums.Category;

import java.math.BigDecimal;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ServiceEntity extends BaseEntity{
    @Column(unique = true)
    private  String title;
    @Enumerated(value = EnumType.STRING)
    private Category category  = Category.MOBILE;
    @Check(name = "commission_check",constraints = "commission >= 0")
    private BigDecimal commission;
    private boolean isActive;
}
