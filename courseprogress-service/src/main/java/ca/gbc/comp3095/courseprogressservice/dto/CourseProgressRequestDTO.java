package ca.gbc.comp3095.courseprogressservice.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CourseProgressRequestDTO {

    @NotNull
    private Long courseId;

    private double accumulatedPercentPoints;
    private double usedPercentPoints;
    private double lostPercentPoints;
    private double maxPossiblePercent;
    private double currentGradePercent;

    private boolean canMeetGoal;

    @NotNull
    private LocalDate weekOf;

    // Optional: allow client to omit and server will set it
    private LocalDateTime computedAt;

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
