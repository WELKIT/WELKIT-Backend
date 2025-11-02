package welkit_server.domain.auth.signup.google.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import welkit_server.domain.auth.signup.google.dto.GoogleSignupRequest;
import welkit_server.domain.mypage.entity.LockSetting;
import welkit_server.domain.mypage.model.FeatureName;
import welkit_server.domain.mypage.repository.LockSettingRepository;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.model.EmailType;
import welkit_server.domain.user.model.JobRole;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.exception.model.NotFoundException;
import welkit_server.global.redis.RedisUtil;
import welkit_server.global.security.jwt.JWTUtil;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GoogleSignupService {

    private final UserRepository userRepository;
    private final LockSettingRepository lockSettingRepository;
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    public String handleGoogleCallback(String code) {
        String email = getEmailFromCode(code);
        boolean isExistingUser = existsByGoogleEmail(email);

        redisUtil.saveCodeEmail(code, email);

        if (isExistingUser) {
            String token = login(email);
            return String.format("http://localhost:3000/dictionary?token=%s", token);
        } else {
            return String.format("http://localhost:3000/users/signup/google?code=%s", code);
        }
    }

    public String signupWithGoogle(String code, GoogleSignupRequest request) {
        String email = redisUtil.getEmailByCode(code);
        if (email == null) {
            throw new BadRequestException(ErrorMessage.INVALID_EMAIL_VERIFICATION);
        }

        signup(email, request.getJobRole());
        String token = login(email);

        redisUtil.deleteCode(code);

        return token;
    }

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

    public void signup(String email, JobRole jobRole) {
        if (jobRole == null) {
            throw new BadRequestException(ErrorMessage.WK_VALIDATION_NULL_OR_BLANK);
        }
        if (userRepository.existsByGoogleEmail(email)) {
            throw new BadRequestException(ErrorMessage.DUPLICATE_EMAIL);
        }

        boolean isCompany = !email.endsWith("@gmail.com");

        User user = User.builder()
                .googleEmail(email)
                .jobRole(jobRole)
                .isCompanyVerified(isCompany)
                .emailType(isCompany ? EmailType.COMPANY_EMAIL : EmailType.PERSONAL_EMAIL)
                .build();

        userRepository.save(user);

        List<LockSetting> lockSettings = Arrays.stream(FeatureName.values())
                .map(feature -> LockSetting.builder()
                        .user(user)
                        .featureName(feature)
                        .isLocked(false)
                        .build())
                .toList();

        lockSettingRepository.saveAll(lockSettings);
    }

    public String login(String email) {
        User user = userRepository.findByGoogleEmail(email)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));

        return jwtUtil.createJwt(
                user.getLoginEmail(),
                user.getId(),
                user.getUserType().name(),
                user.getJobRole().name(),
                60 * 60 * 1000L
        );
    }

    public boolean existsByGoogleEmail(String email) {
        return userRepository.existsByGoogleEmail(email);
    }
}