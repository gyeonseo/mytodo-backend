package my.planner.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "todoStatus")
public class TodoStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date; // 예: 2025-07-18

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public TodoStatus(LocalDate requestDate, Status newStatus, Category category) {
        this.date = requestDate;
        this.status = newStatus;
        this.category = category;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
    // status에 대한 Setter 나중에 없애기
}
