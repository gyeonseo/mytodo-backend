package my.planner.dto.category;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryResponseDto {

    // 응답 DTO << 조회용으로 사용. 사용자 정보 필요 X
    private Long id;
    private String name;
    private String color;
}
