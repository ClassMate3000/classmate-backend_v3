package ca.gbc.comp3095.courseservice.model;

// JAMES - Purpose: Represent how course data is stored internally, NOT how it's exposed
// JAMES - @Entity -> JPA-managed persistence
// JAMES - @Table(name = "courses") -> explicit DB mapping
// JAMES - No validation annotations here -> entities are NOT API contracts

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// PENNY - Added all the properties based on updated UML - removed 'description' property
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long courseId; // PENNY - Renamed to courseId - TODO: What else is affected by this

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String instructor;

    // PENNY - List of meeting times / scheduled class times
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "course_meetings", joinColumns = @JoinColumn(name = "course_id"))
    private List<CourseMeeting> meetings = new ArrayList<>();

    @Column(nullable = false)
    private int gradeGoal;

    @Column(nullable = false)
    private LocalDate startWeek;

    // JPA requires a no-args constructor
    public Course() {
    }

    // Constructors
    public Course(String code,
                  String title,
                  String instructor,
                  List<CourseMeeting> meetings,
                  int gradeGoal,
                  LocalDate startWeek) {
        this.code = code;
        this.title = title;
        this.instructor = instructor;
        this.meetings = (meetings != null) ? meetings : new ArrayList<>();
        this.gradeGoal = gradeGoal;
        this.startWeek = startWeek;
    }

    public Long getCourseId() { return courseId; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }

    public List<CourseMeeting> getMeetings() { return meetings; }
    public void setMeetings(List<CourseMeeting> meetings) {
        this.meetings = (meetings != null) ? meetings : new ArrayList<>();
    }

    public int getGradeGoal() { return gradeGoal; }
    public void setGradeGoal(int gradeGoal) { this.gradeGoal = gradeGoal; }

    public LocalDate getStartWeek() { return startWeek; }
    public void setStartWeek(LocalDate startWeek) { this.startWeek = startWeek; }
}
