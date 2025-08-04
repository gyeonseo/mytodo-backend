package my.planner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    private String name; // 예: "운동", "영어공부"
    private String color; // ex: "#FF99CC"


    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 사용자별 카테고리

    public Category(String name, String color, User user) {
        this.name = name;
        this.color = color;
        this.user = user;
    }

}
