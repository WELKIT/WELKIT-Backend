package welkit_server.domain.auth.login.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import welkit_server.domain.auth.login.dto.LoginRequest;
import welkit_server.domain.user.entity.User;
import welkit_server.domain.user.repository.UserRepository;
import welkit_server.global.exception.message.ErrorMessage;
import welkit_server.global.exception.model.BadRequestException;
import welkit_server.global.security.jwt.JWTUtil;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException(ErrorMessage.INVALID_CREDENTIAL));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadRequestException(ErrorMessage.INVALID_CREDENTIAL);
        }

        return jwtUtil.createJwt(
                user.getEmail(),
                user.getId(),
                user.getUserType().name(),
                60 * 60 * 1000L
        );
    }
}
