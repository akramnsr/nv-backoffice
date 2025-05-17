package com.elearning.web;

import com.elearning.security.JwtFilter;
import com.elearning.security.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest {

    @MockBean
    protected JwtFilter jwtFilter;

    @MockBean
    protected JwtUtil jwtUtil;

    @BeforeEach
    void setupJwtFilter() throws ServletException, IOException {
        // 1) On dit à JwtUtil de toujours considérer le token comme valide
        given(jwtUtil.validateToken(any(String.class))).willReturn(true);

        // 2) On stubbe l’extraction du username et du rôle
        given(jwtUtil.getUsernameFromToken(any(String.class)))
                .willReturn("anouasria360@gmail.com");
        given(jwtUtil.getRoleFromToken(any(String.class)))
                .willReturn("ADMIN");

        // 3) On fait passer le filtre, mais on injecte une authentification ROLE_ADMIN dans le SecurityContext
        BDDMockito.willAnswer(invocation -> {
            ServletRequest req   = invocation.getArgument(0);
            ServletResponse res  = invocation.getArgument(1);
            FilterChain chain    = invocation.getArgument(2);

            var auth = new UsernamePasswordAuthenticationToken(
                    jwtUtil.getUsernameFromToken("ignored"),
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            chain.doFilter(req, res);
            return null;
        }).given(jwtFilter).doFilter(
                any(ServletRequest.class),
                any(ServletResponse.class),
                any(FilterChain.class)
        );
    }
}
