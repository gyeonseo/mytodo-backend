package my.planner.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import my.planner.domain.User;
import my.planner.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/api/users/signup") || path.equals("/api/users/login");
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        // 1. 토큰이 없거나 잘못된 형식이면 401 반환
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // 인증이 필요한 URL인지 체크
            String path = request.getRequestURI();
            if (requiresAuthentication(path)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                response.setContentType("application/json;charset=UTF-8");
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }

        // 2. JWT 토큰 추출
        String token = authHeader.substring(7);
        String username = jwtUtil.extractUsername(token);

        // 3. 사용자 정보 확인 & SecurityContext에 등록
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userRepository.findByUsername(username).orElse(null);

            if (user != null && jwtUtil.isTokenValid(token, username)) {
                CustomUserDetails userDetails = new CustomUserDetails(user);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                // 토큰은 있는데 잘못된 경우도 401
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    // 인증이 필요한 URL인지 체크하는 메서드
    private boolean requiresAuthentication(String path) {
        // permitAll 한 URL들은 제외
        return !(path.equals("/api/users/signup")
                || path.equals("/api/users/login")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs"));
    }


}
