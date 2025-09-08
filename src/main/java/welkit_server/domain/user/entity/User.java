package welkit_server.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import welkit_server.domain.user.model.*;
import welkit_server.global.domain.BaseEntity;

@Entity
@Getter
@Builder
@Table(name="users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 50)
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private EmailType emailType;

    @Column(name = "google_email", length = 50)
    private String googleEmail;

    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    @Pattern(regexp = "^(?=.*[!@#$%^&*()\\-_=+\\[\\]{};:'\",.<>/?]).+$",
            message = "비밀번호는 최소 1개의 특수문자를 포함해야 합니다.")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "job_role", nullable = false, length = 40)
    private JobRole jobRole;

    @Column(name = "is_company_verified", nullable = false)
    private boolean isCompanyVerified;

    //기능 잠금 설정용 비밀번호
    @Column(name = "lock_pin", length=45)
    private String lockPin;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 25)
    private UserType userType = UserType.USER;

    public String getLoginEmail() {
        return this.email != null ? this.email : this.googleEmail;
    }

}
