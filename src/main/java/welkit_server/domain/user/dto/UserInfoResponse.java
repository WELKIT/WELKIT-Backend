package welkit_server.domain.user.dto;

import lombok.*;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.EmailType;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.model.UserType;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserInfoResponse {
    private Long id;
    private String email;
    private EmailType emailType;
    private boolean isCompanyVerified;
    private JobRole jobRole;
    private UserType userType;

    public static UserInfoResponse fromEntity(User user) {
        return UserInfoResponse.builder()
                .id(user.getId())
                .email(user.getEmail() != null ? user.getEmail() : user.getGoogleEmail())
                .emailType(user.getEmailType())
                .isCompanyVerified(user.isCompanyVerified())
                .jobRole(user.getJobRole())
                .userType(user.getUserType())
                .build();
    }
}
