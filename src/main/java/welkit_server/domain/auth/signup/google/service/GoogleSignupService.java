// GoogleSignupService.java
package welkit_server.domain.auth.signup.google.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import welkit_server.domain.auth.signup.google.dto.GoogleSignupRequest;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.EmailType;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleSignupService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    // 세션에 이메일 저장
    public void saveGoogleEmailToSession(String code, HttpSession session) {
        String email = getEmailFromCode(code);
        if (email == null || email.isEmpty()) {
            throw new BadRequestException(ErrorMessage.INVALID_EMAIL_VERIFICATION);
        }
        session.setAttribute("googleEmail", email);
        System.out.println("Google Email saved in session: " + email);
    }

    // 회원가입 처리
    public void signupWithSessionEmail(HttpSession session, GoogleSignupRequest request) {
        String email = (String) session.getAttribute("googleEmail");
        if (email == null) {
            throw new BadRequestException(ErrorMessage.INVALID_EMAIL_VERIFICATION);
        }

        signup(email, request.getPassword(), request.getJobRole());
        session.removeAttribute("googleEmail");
    }

    // 구글 코드로 이메일 가져오기
    public String getEmailFromCode(String code) {
        String tokenUrl = "https://oauth2.googleapis.com/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(tokenUrl, request, Map.class);

        if (tokenResponse.getBody() == null || !tokenResponse.getBody().containsKey("access_token")) {
            throw new BadRequestException(ErrorMessage.INVALID_EMAIL_VERIFICATION);
        }

        String accessToken = (String) tokenResponse.getBody().get("access_token");

        String userInfoUrl = "https://www.googleapis.com/oauth2/v2/userinfo";
        HttpHeaders authHeaders = new HttpHeaders();
        authHeaders.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(authHeaders);

        ResponseEntity<Map> response = restTemplate.exchange(userInfoUrl, HttpMethod.GET, entity, Map.class);
        Map<String, Object> body = response.getBody();
        if (body == null || !body.containsKey("email")) {
            throw new BadRequestException(ErrorMessage.INVALID_EMAIL_VERIFICATION);
        }

        return (String) body.get("email");
    }

    // DB 저장
    public void signup(String email, String password, JobRole jobRole) {
        if (userRepository.existsByEmail(email)) {
            throw new BadRequestException(ErrorMessage.DUPLICATE_EMAIL);
        }

        boolean isCompany = !email.endsWith("@gmail.com");

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .jobRole(jobRole)
                .isCompanyVerified(isCompany)
                .emailType(isCompany ? EmailType.COMPANY_EMAIL : EmailType.PERSONAL_EMAIL)
                .build();

        userRepository.save(user);
    }

}