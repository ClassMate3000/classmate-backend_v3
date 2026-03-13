package ca.gbc.comp3095.courseprogressservice.model;

import jakarta.persistence.*;
// PENNY - added these imports
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "course_progress")
public class CourseProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_id")
    private Long progressId;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(nullable = false)
    private double accumulatedPercentPoints;

    @Column(nullable = false)
    private double usedPercentPoints;

    @Column(nullable = false)
    private double lostPercentPoints;

    @Column(nullable = false)
    private double maxPossiblePercent;

    @Column(nullable = false)
    private double currentGradePercent;

    @Column(nullable = false)
    private boolean canMeetGoal;

    @Column(name = "week_of", nullable = false)
    private LocalDate weekOf;

    @Column(name = "computed_at", nullable = false)
    private LocalDateTime computedAt;

    // JAMES - JPA requires a no-args constructor (shows an error if not added)
    protected CourseProgress() {}

    // optional constructor for convenience
    public CourseProgress(Long courseId,
                          double accumulatedPercentPoints,
                          double usedPercentPoints,
                          double lostPercentPoints,
                          double maxPossiblePercent,
                          double currentGradePercent,
                          boolean canMeetGoal,
                          LocalDate weekOf,
                          LocalDateTime computedAt) {
        this.courseId = courseId;
        this.accumulatedPercentPoints = accumulatedPercentPoints;
        this.usedPercentPoints = usedPercentPoints;
        this.lostPercentPoints = lostPercentPoints;
        this.maxPossiblePercent = maxPossiblePercent;
        this.currentGradePercent = currentGradePercent;
        this.canMeetGoal = canMeetGoal;
        this.weekOf = weekOf;
        this.computedAt = computedAt;
    }

    public Long getProgressId() { return progressId; }
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public double getAccumulatedPercentPoints() { return accumulatedPercentPoints; }
    public void setAccumulatedPercentPoints(double v) { this.accumulatedPercentPoints = v; }

    public double getUsedPercentPoints() { return usedPercentPoints; }
    public void setUsedPercentPoints(double v) { this.usedPercentPoints = v; }

    public double getLostPercentPoints() { return lostPercentPoints; }
    public void setLostPercentPoints(double v) { this.lostPercentPoints = v; }

    public double getMaxPossiblePercent() { return maxPossiblePercent; }
    public void setMaxPossiblePercent(double v) { this.maxPossiblePercent = v; }

    public double getCurrentGradePercent() { return currentGradePercent; }
    public void setCurrentGradePercent(double v) { this.currentGradePercent = v; }

    public boolean isCanMeetGoal() { return canMeetGoal; }
    public void setCanMeetGoal(boolean canMeetGoal) { this.canMeetGoal = canMeetGoal; }

    public LocalDate getWeekOf() { return weekOf; }
    public void setWeekOf(LocalDate weekOf) { this.weekOf = weekOf; }

    public LocalDateTime getComputedAt() { return computedAt; }
    public void setComputedAt(LocalDateTime computedAt) { this.computedAt = computedAt; }
}
