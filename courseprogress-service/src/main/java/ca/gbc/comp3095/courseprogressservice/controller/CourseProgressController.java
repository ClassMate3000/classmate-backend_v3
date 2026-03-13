package ca.gbc.comp3095.courseprogressservice.controller;

import ca.gbc.comp3095.courseprogressservice.dto.CourseProgressRequestDTO;
import ca.gbc.comp3095.courseprogressservice.dto.CourseProgressResponseDTO;
import ca.gbc.comp3095.courseprogressservice.service.CourseProgressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/course-progress")
public class CourseProgressController {

    private final CourseProgressService courseProgressService;

    public CourseProgressController(CourseProgressService courseProgressService) {
        this.courseProgressService = courseProgressService;
    }

    @GetMapping
    public ResponseEntity<List<CourseProgressResponseDTO>> getAllProgress() {
        return ResponseEntity.ok(courseProgressService.getAllProgress());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseProgressResponseDTO> getProgressById(@PathVariable Long id) {
        return ResponseEntity.ok(courseProgressService.getProgressById(id));
    }

    @PostMapping
    public ResponseEntity<CourseProgressResponseDTO> createProgress(
            @Valid @RequestBody CourseProgressRequestDTO dto) {

        CourseProgressResponseDTO created = courseProgressService.createProgress(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseProgressResponseDTO> updateProgress(
            @PathVariable Long id,
            @Valid @RequestBody CourseProgressRequestDTO dto) {

        CourseProgressResponseDTO updated = courseProgressService.updateProgress(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long id) {
        courseProgressService.deleteProgress(id);
        return ResponseEntity.noContent().build();
    }
}
