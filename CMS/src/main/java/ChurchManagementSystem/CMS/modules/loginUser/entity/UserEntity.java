package ChurchManagementSystem.CMS.modules.loginUser.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min=3, max = 20)
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min=10, max = 14)
    private String phone;

    @CreationTimestamp
    private LocalDateTime regDateTime;

    public UserEntity(String username, String email, String phone, LocalDateTime regDateTime) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.regDateTime = regDateTime;
    }
}
