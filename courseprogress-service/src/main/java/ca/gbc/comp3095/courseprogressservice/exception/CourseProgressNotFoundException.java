package ca.gbc.comp3095.courseprogressservice.exception;

public class CourseProgressNotFoundException extends RuntimeException {
    public CourseProgressNotFoundException(Long id) {
        super("CourseProgress not found with id: " + id);
    }
}
