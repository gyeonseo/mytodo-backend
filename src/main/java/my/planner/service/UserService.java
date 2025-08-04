package my.planner.service;

import lombok.RequiredArgsConstructor;
import my.planner.domain.User;
import my.planner.dto.user.LoginResponse;
import my.planner.dto.user.LoginRequest;
import my.planner.dto.user.UserResponseDto;
import my.planner.dto.user.UserSignupRequestDto;
import my.planner.jwt.JwtUtil;
import my.planner.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserResponseDto signup(UserSignupRequestDto request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword()); // ✅ 암호화
        User user = new User(request.getUsername(), encodedPassword);
        userRepository.save(user);

        return new UserResponseDto(user.getId(), user.getUsername());
    }


    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.createToken(user.getUsername());

        return new LoginResponse(token);
    }

}

