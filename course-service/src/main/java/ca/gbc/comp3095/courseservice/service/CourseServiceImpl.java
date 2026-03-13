package ca.gbc.comp3095.courseservice.service;

/* JAMES -
 * This ensures that the controller stays thin
 * Mapping happens in service
 * Exceptions are domain-specific
 */

import ca.gbc.comp3095.courseservice.dto.CourseRequestDTO;
import ca.gbc.comp3095.courseservice.dto.CourseResponseDTO;
import ca.gbc.comp3095.courseservice.exception.CourseNotFoundException;
import ca.gbc.comp3095.courseservice.model.Course;
import ca.gbc.comp3095.courseservice.model.CourseMeeting;
import ca.gbc.comp3095.courseservice.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseResponseDTO> getAllCourses() {
        return courseRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    @Override
    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return toResponseDTO(course);
    }

    @Override
    public CourseResponseDTO createCourse(CourseRequestDTO dto) {
        Course course = new Course(
                dto.getCode(),
                dto.getTitle(),
                dto.getInstructor(),
                dto.getMeetings().stream()
                        .map(m -> new CourseMeeting(m.getDayOfWeek(), m.getStartTime(), m.getEndTime()))
                        .toList(),
                dto.getGradeGoal(),
                dto.getStartWeek()
        );

        Course saved = courseRepository.save(course);
        return toResponseDTO(saved);
    }

    @Override
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));

        course.setCode(dto.getCode());
        course.setTitle(dto.getTitle());
        course.setInstructor(dto.getInstructor());
        course.setMeetings(dto.getMeetings().stream()
                .map(m -> new CourseMeeting(m.getDayOfWeek(), m.getStartTime(), m.getEndTime()))
                .toList());
        course.setGradeGoal(dto.getGradeGoal());
        course.setStartWeek(dto.getStartWeek());

        Course saved = courseRepository.save(course);
        return toResponseDTO(saved);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        courseRepository.delete(course);
    }

    private CourseResponseDTO toResponseDTO(Course course) {
        var meetingDtos = course.getMeetings().stream()
                .map(m -> new CourseResponseDTO.MeetingDTO(
                        m.getDayOfWeek(), m.getStartTime(), m.getEndTime()
                ))
                .toList();

        return new CourseResponseDTO(
                course.getCourseId(),
                course.getCode(),
                course.getTitle(),
                course.getInstructor(),
                new ArrayList<>(meetingDtos),
                course.getGradeGoal(),
                course.getStartWeek()
        );
    }
}
