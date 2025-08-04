package my.planner.dto.calendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import my.planner.domain.Status;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class TodoStatusDto {
    private LocalDate date;
    private Status status;
}
