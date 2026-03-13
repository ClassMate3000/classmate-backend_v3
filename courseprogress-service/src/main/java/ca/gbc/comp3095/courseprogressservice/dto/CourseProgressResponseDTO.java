package ca.gbc.comp3095.courseprogressservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class CourseProgressResponseDTO {

    private final Long progressId;
    private final Long courseId;

    private final double accumulatedPercentPoints;
    private final double usedPercentPoints;
    private final double lostPercentPoints;
    private final double maxPossiblePercent;
    private final double currentGradePercent;

    private final boolean canMeetGoal;

    private final LocalDate weekOf;
    private final LocalDateTime computedAt;

   // JAMES -- Complete / Full Constructor needed for the Service layer -- //
    public CourseProgressResponseDTO(
            Long progressId,
            Long courseId,
            double accumulatedPercentPoints,
            double usedPercentPoints,
            double lostPercentPoints,
            double maxPossiblePercent,
            double currentGradePercent,
            boolean canMeetGoal,
            LocalDate weekOf,
            LocalDateTime computedAt
    ) {
        this.progressId = progressId;
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

    public double getAccumulatedPercentPoints() { return accumulatedPercentPoints; }
    public double getUsedPercentPoints() { return usedPercentPoints; }
    public double getLostPercentPoints() { return lostPercentPoints; }
    public double getMaxPossiblePercent() { return maxPossiblePercent; }
    public double getCurrentGradePercent() { return currentGradePercent; }

    public boolean isCanMeetGoal() { return canMeetGoal; }

    public LocalDate getWeekOf() { return weekOf; }
    public LocalDateTime getComputedAt() { return computedAt; }
}
