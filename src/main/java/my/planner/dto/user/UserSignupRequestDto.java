package my.planner.dto.user;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserSignupRequestDto {
    @Size(min = 4, message = "아이디는 최소 4글자 이상이어야 합니다.")
    private String username;

    @Size(min = 4, message = "비밀번호는 최소 4글자 이상이어야 합니다.")
    private String password;
}
