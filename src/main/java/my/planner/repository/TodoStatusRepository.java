package my.planner.repository;

import my.planner.domain.Category;
import my.planner.domain.Status;
import my.planner.domain.TodoStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoStatusRepository extends JpaRepository<TodoStatus, Long> {

    @Query("""
    SELECT t
    FROM TodoStatus t
    WHERE t.category.id = :categoryId
      AND t.date >= :startDate
      AND t.date < :endDate
""")
    List<TodoStatus> findByCategoryIdAndDateRange(@Param("categoryId") Long categoryId,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate);


    Optional<TodoStatus> findByCategoryAndDate(Category category, LocalDate date);
}
