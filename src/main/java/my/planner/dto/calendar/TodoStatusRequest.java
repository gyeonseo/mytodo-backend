package my.planner.dto.calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import my.planner.domain.Status;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TodoStatusRequest {
    private LocalDate date;
    private Status status; // 예: DONE, FAIL, SKIPPED 등
}
