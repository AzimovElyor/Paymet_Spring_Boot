package uz.pdp.springboot.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.pdp.springboot.enums.UserRole;


@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Builder
@Table(name = "payment_user")
public class User extends BaseEntity{
    @Column(nullable = false)
    String name;
    @Column(unique = true)
    String username;
    @Column(nullable = false )
    Integer age;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private boolean isActive;
    @Column(nullable = false)
    String password;

}
