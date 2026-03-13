package ca.gbc.comp3095.courseservice.controller;

/* JAMES - 
* This ensures:
*              - versioning baked in
*              - correct status codes
*              - validation enforced
*              - no business logic in controller*
*/


import ca.gbc.comp3095.courseservice.dto.CourseRequestDTO;
import ca.gbc.comp3095.courseservice.dto.CourseResponseDTO;
import ca.gbc.comp3095.courseservice.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses(){
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("UP");
    }

    // PENNY - updated id - courseId
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponseDTO> getCourseById(@PathVariable Long courseId){
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }

    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(@Valid @RequestBody CourseRequestDTO dto){

        CourseResponseDTO created = courseService.createCourse(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // JAMES -- New endpoint: updateCourse() = update -- //
    // PENNY - updated id - courseId
    @PutMapping("/{courseId}")
    public ResponseEntity<CourseResponseDTO> updateCourse(
            @PathVariable Long courseId,
            @Valid @RequestBody CourseRequestDTO dto){
        CourseResponseDTO updated = courseService.updateCourse(courseId, dto);
        return ResponseEntity.ok(updated);
    }
    
    // JAMES -- New endpoint: deleteCourse() = delete -- //
    // PENNY - updated id - courseId
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId){
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();    // JAMES - 204 No Content
    }
}


