package welkit_server.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import welkit_server.domain.user.service.UserService;
import welkit_server.global.dto.SuccessResponse;
import welkit_server.global.exception.message.SuccessMessage;
import welkit_server.global.security.dto.CustomUserDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResponse> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deleteUser(userDetails.getUser());
        return ResponseEntity.ok(SuccessResponse.of(SuccessMessage.DELETE_USER_SUCCESS));
    }

}