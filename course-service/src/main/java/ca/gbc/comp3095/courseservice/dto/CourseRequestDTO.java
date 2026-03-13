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

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CourseRequestDTO {

    @NotBlank(message = "Course code is required")
    @Size(max = 10, message = "Course code must be at most 10 characters")
    private String code;

    @NotBlank(message = "Course title is required")
    private String title;

    @NotBlank(message = "Instructor is required")
    private String instructor;

    @NotNull(message = "Meetings is required")
    @Size(min = 1, message = "At least one meeting is required")
    private List<@Valid MeetingDTO> meetings;

    @NotNull(message = "Grade goal is required")
    private Integer gradeGoal;

    @NotNull(message = "Start week is required")
    private LocalDate startWeek;

    public static class MeetingDTO {
        @NotNull @Min(1) @Max(7)
        private Integer dayOfWeek;

        @NotNull
        private LocalTime startTime;

        @NotNull
        private LocalTime endTime;

        public Integer getDayOfWeek() { return dayOfWeek; }
        public void setDayOfWeek(Integer dayOfWeek) { this.dayOfWeek = dayOfWeek; }

        public LocalTime getStartTime() { return startTime; }
        public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

        public LocalTime getEndTime() { return endTime; }
        public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public List<MeetingDTO> getMeetings() { return meetings; }
    public void setMeetings(List<MeetingDTO> meetings) { this.meetings = meetings; }

    public Integer getGradeGoal() { return gradeGoal; }
    public void setGradeGoal(Integer gradeGoal) { this.gradeGoal = gradeGoal; }

    public LocalDate getStartWeek() { return startWeek; }
    public void setStartWeek(LocalDate startWeek) { this.startWeek = startWeek; }
}