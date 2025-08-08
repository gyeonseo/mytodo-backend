package my.planner.controller;

import lombok.RequiredArgsConstructor;
import my.planner.dto.user.LoginRequest;
import my.planner.dto.user.LoginResponse;
import my.planner.dto.user.UserResponseDto;
import my.planner.dto.user.UserSignupRequestDto;
import my.planner.jwt.CustomUserDetails;
import my.planner.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(
            @Valid @RequestBody UserSignupRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        UserResponseDto userResponse = userService.getUserByUsername(username);
        return ResponseEntity.ok(userResponse);
    }
}
