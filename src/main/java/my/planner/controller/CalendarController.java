package my.planner.controller;

import lombok.RequiredArgsConstructor;
import my.planner.dto.calendar.TodoStatusDto;
import my.planner.dto.calendar.TodoStatusRequest;
import my.planner.jwt.CustomUserDetails;
import my.planner.service.AuthorizationService;
import my.planner.service.CalendarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories/{categoryId}/calendar")
public class CalendarController {

    private final CalendarService calendarService;
    private final AuthorizationService authorizationService;

    @GetMapping
    public ResponseEntity<List<TodoStatusDto>> getCalendar(
            @PathVariable Long categoryId,
            @RequestParam int year,
            @RequestParam int month,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUser().getId();

        // 권한 확인
        if (!authorizationService.userOwnsCategory(userId, categoryId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<TodoStatusDto> calendar = calendarService.getCalendar(categoryId, year, month);
        return ResponseEntity.ok(calendar);
    }

    @PostMapping
    public ResponseEntity<Void> updateDailyStatus(
            @PathVariable Long categoryId,
            @RequestBody TodoStatusRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long userId = userDetails.getUser().getId();

        if (!authorizationService.userOwnsCategory(userId, categoryId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        calendarService.updateDailyStatus(categoryId, request);
        return ResponseEntity.ok().build();
    }
}
