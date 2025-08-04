package my.planner.service;


import lombok.RequiredArgsConstructor;
import my.planner.domain.Category;
import my.planner.domain.User;
import my.planner.dto.category.CategoryRequestDto;
import my.planner.dto.category.CategoryResponseDto;
import my.planner.jwt.CustomUserDetails;
import my.planner.repository.CategoryRepository;
import my.planner.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryResponseDto create(CategoryRequestDto request, CustomUserDetails userDetails) {
        User user = getUser(userDetails);
        Category category = new Category(request.getName(), request.getColor(), user);
        Category saved = categoryRepository.save(category);
        return new CategoryResponseDto(saved.getId(), saved.getName(), saved.getColor());
    }

    public CategoryResponseDto findById(Long id, CustomUserDetails userDetails) {
        Category category = getOwnedCategory(id, userDetails);
        return new CategoryResponseDto(category.getId(), category.getName(), category.getColor());
    }

    public List<CategoryResponseDto> findAll(CustomUserDetails userDetails) {
        User user = getUser(userDetails);
        return categoryRepository.findAllByUser(user).stream()
                .map(c -> new CategoryResponseDto(c.getId(), c.getName(), c.getColor()))
                .toList();
    }

    public CategoryResponseDto update(Long id, CategoryRequestDto request, CustomUserDetails userDetails) {
        Category category = getOwnedCategory(id, userDetails);
        category.setName(request.getName());
        category.setColor(request.getColor());
        Category updated = categoryRepository.save(category);
        return new CategoryResponseDto(updated.getId(), updated.getName(), updated.getColor());
    }

    public void delete(Long id, CustomUserDetails userDetails) {
        Category category = getOwnedCategory(id, userDetails);
        categoryRepository.delete(category);
    }

    // 공통 유저 조회
    private User getUser(CustomUserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    // 카테고리 주인 확인 로직
    private Category getOwnedCategory(Long categoryId, CustomUserDetails userDetails) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        if (!category.getUser().getUsername().equals(userDetails.getUsername())) {
            //throw new AccessDeniedException("본인의 카테고리만 조회/수정/삭제할 수 있습니다.");
        }
        return category;
    }
}
