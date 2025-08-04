package my.planner.controller;

import lombok.RequiredArgsConstructor;
import my.planner.dto.category.CategoryRequestDto;
import my.planner.dto.category.CategoryResponseDto;
import my.planner.jwt.CustomUserDetails;
import my.planner.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@PreAuthorize("isAuthenticated()")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(@RequestBody CategoryRequestDto request,
                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        CategoryResponseDto response = categoryService.create(request, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> findById(@PathVariable Long id,
                                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        CategoryResponseDto response = categoryService.findById(id, userDetails);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<CategoryResponseDto> categories = categoryService.findAll(userDetails);
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable Long id,
                                                      @RequestBody CategoryRequestDto request,
                                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        CategoryResponseDto response = categoryService.update(id, request, userDetails);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @AuthenticationPrincipal CustomUserDetails userDetails) {
        categoryService.delete(id, userDetails);
        return ResponseEntity.noContent().build();
    }
}

