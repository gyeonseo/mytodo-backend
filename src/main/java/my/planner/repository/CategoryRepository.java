package my.planner.repository;

import my.planner.domain.Category;
import my.planner.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByUser(User user);
    boolean existsByIdAndUserId(Long categoryId, Long userId);
}
