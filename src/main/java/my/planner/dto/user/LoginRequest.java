package my.planner.dto.user;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter  // 나중에 builder 처리하기
@AllArgsConstructor
public class LoginRequest {
    private String username;
    private String password;
}

