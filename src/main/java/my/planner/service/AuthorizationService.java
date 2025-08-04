package my.planner.service;

import lombok.RequiredArgsConstructor;
import my.planner.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {
    private final CategoryRepository categoryRepository;

    public boolean userOwnsCategory(Long userId, Long categoryId) {
        return categoryRepository.existsByIdAndUserId(categoryId, userId);
    }
}