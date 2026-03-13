package ca.gbc.comp3095.courseservice.dto;

/* JAMES - 
 * Purpose:
 *        - Prevent exposing internal DB structure
 *        - Allow API evolution without breaking clients
 *
 * Request DTO has validation
 * Response DTO is read-only
 * No setters in response DTO -> immutability
 */

// PENNY - Added imports
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CourseResponseDTO {

    private final Long courseId;
    private final String code;
    private final String title;
    private final String instructor;
    private final List<MeetingDTO> meetings;
    private final int gradeGoal;
    private final LocalDate startWeek;

    public static class MeetingDTO {
        private final Integer dayOfWeek;
        private final LocalTime startTime;
        private final LocalTime endTime;

        public MeetingDTO(Integer dayOfWeek, LocalTime startTime, LocalTime endTime) {
            this.dayOfWeek = dayOfWeek;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public Integer getDayOfWeek() { return dayOfWeek; }
        public LocalTime getStartTime() { return startTime; }
        public LocalTime getEndTime() { return endTime; }
    }

    public CourseResponseDTO(Long courseId, String code, String title, String instructor,
                             List<MeetingDTO> meetings, int gradeGoal, LocalDate startWeek) {
        this.courseId = courseId;
        this.code = code;
        this.title = title;
        this.instructor = instructor;
        this.meetings = meetings;
        this.gradeGoal = gradeGoal;
        this.startWeek = startWeek;
    }

    public Long getCourseId() { return courseId; }
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public String getInstructor() { return instructor; }
    public List<MeetingDTO> getMeetings() { return meetings; }
    public int getGradeGoal() { return gradeGoal; }
    public LocalDate getStartWeek() { return startWeek; }
}