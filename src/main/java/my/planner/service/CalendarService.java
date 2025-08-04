package my.planner.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import my.planner.domain.Category;
import my.planner.domain.TodoStatus;
import my.planner.domain.Status;
import my.planner.dto.calendar.TodoStatusDto;
import my.planner.dto.calendar.TodoStatusRequest;
import my.planner.repository.CategoryRepository;
import my.planner.repository.TodoStatusRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final TodoStatusRepository todoStatusRepository;
    private final CategoryRepository categoryRepository;

    public List<TodoStatusDto> getCalendar(Long categoryId, int year, int month) {
        // 해당 연월의 날짜 리스트 생성
        List<LocalDate> dates = generateDates(year, month);

        // DB에서 해당 카테고리의 날짜 상태 가져오기
        List<TodoStatus> savedStatuses =
                todoStatusRepository.findByCategoryIdAndMonth(categoryId, year, month);

        // 날짜별 상태 매핑
        Map<LocalDate, Status> statusMap = savedStatuses.stream()
                .collect(Collectors.toMap(TodoStatus::getDate, TodoStatus::getStatus));

        // DTO 변환 (없는 날짜는 NONE 처리)
        return dates.stream()
                .map(date -> new TodoStatusDto(date, statusMap.getOrDefault(date, Status.NONE)))
                .collect(Collectors.toList());
    }

    // 한 달짜리 날짜 리스트 생성
    private List<LocalDate> generateDates(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return IntStream.rangeClosed(1, yearMonth.lengthOfMonth())
                .mapToObj(day -> LocalDate.of(year, month, day))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateDailyStatus(Long categoryId, TodoStatusRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));

        LocalDate requestDate = request.getDate();
        Status newStatus = request.getStatus();

        Optional<TodoStatus> optional = todoStatusRepository.findByCategoryAndDate(category, requestDate);

        if (optional.isPresent()) {
            TodoStatus existing = optional.get();
            existing.updateStatus(newStatus);
        } else {
            TodoStatus newStatusEntity = new TodoStatus(requestDate, newStatus, category);
            todoStatusRepository.save(newStatusEntity);
        }
    }
}
