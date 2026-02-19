-- ===============================
-- CourseProgress Service - DEV Seed Data #nezihe
-- ===============================
SET search_path TO courseprogress_service;

DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM course_progress) THEN
    INSERT INTO course_progress (
      course_id,
      accumulated_percent_points,
      used_percent_points,
      lost_percent_points,
      max_possible_percent,
      current_grade_percent,
      can_meet_goal,
      week_of,
      computed_at
    ) VALUES
      (1, 20, 5, 0, 100, 85, TRUE, DATE '2026-02-16', NOW()),
      (2, 15, 3, 0, 100, 88, TRUE, DATE '2026-02-16', NOW());
  END IF;
END $$;
