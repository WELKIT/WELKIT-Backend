package welkit_server.filter.user;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import welkit_server.global.security.jwt.JWTUtil;
import welkit_server.global.security.jwt.LoginFilter;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class LoginFilterTest {
    private LoginFilter loginFilter;
    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;

    @BeforeEach
    void setUp() {
            authenticationManager = mock(AuthenticationManager.class);
            jwtUtil = mock(JWTUtil.class);
            loginFilter = new LoginFilter(authenticationManager, jwtUtil);
    }

    @DisplayName("로그인 요청을 정상적으로 처리하고 인증을 위임한다")
    @Test
    void loginFilterTest() throws ServletException, IOException {
        //given
       MockHttpServletRequest request = new MockHttpServletRequest();
       request.setMethod("POST");
       request.setParameter("username", "user6@test.com");
       request.setParameter("password", "qwer1234!");

       MockHttpServletResponse response = new MockHttpServletResponse();

       Authentication authResult = mock(Authentication.class);
       when(authenticationManager.authenticate(any())).thenReturn(authResult);

       //when
        Authentication result = loginFilter.attemptAuthentication(request, response);

        //then
        assertThat(result).isSameAs(authResult);
        verify(authenticationManager, times(1)).authenticate(any());
    }

}

