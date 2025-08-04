package my.planner.controller;

import lombok.RequiredArgsConstructor;
import my.planner.dto.user.LoginRequest;
import my.planner.dto.user.LoginResponse;
import my.planner.dto.user.UserResponseDto;
import my.planner.dto.user.UserSignupRequestDto;
import my.planner.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserSignupRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

}
