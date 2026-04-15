-- ===============================
-- CourseProgress Service - Update Seed Data
-- ===============================

SET search_path TO courseprogress_service;

-- Clear existing and re-seed with richer data for all 5 courses
DELETE FROM course_progress;

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
  -- COMP3095 Capstone Project I (courseId=1) - doing well
  (1, 35.0, 10.0, 2.0, 100.0, 85.0, TRUE,  DATE '2026-02-16', NOW()),
  (1, 50.0, 15.0, 3.0, 100.0, 86.5, TRUE,  DATE '2026-03-02', NOW()),
  (1, 65.0, 25.0, 5.0, 100.0, 84.2, TRUE,  DATE '2026-03-16', NOW()),
  (1, 72.0, 30.0, 5.0, 100.0, 83.8, TRUE,  DATE '2026-03-30', NOW()),

  -- COMP3133 Full Stack Dev II (courseId=2) - strong performance
  (2, 30.0,  8.0, 1.0, 100.0, 88.0, TRUE,  DATE '2026-02-16', NOW()),
  (2, 48.0, 12.0, 2.0, 100.0, 89.5, TRUE,  DATE '2026-03-02', NOW()),
  (2, 60.0, 20.0, 3.0, 100.0, 87.0, TRUE,  DATE '2026-03-16', NOW()),
  (2, 70.0, 28.0, 4.0, 100.0, 86.0, TRUE,  DATE '2026-03-30', NOW()),

  -- COMP3078 Mobile App Dev (courseId=3) - struggling slightly
  (3, 20.0,  8.0, 3.0, 100.0, 72.0, TRUE,  DATE '2026-02-16', NOW()),
  (3, 35.0, 15.0, 6.0, 100.0, 70.5, TRUE,  DATE '2026-03-02', NOW()),
  (3, 45.0, 20.0, 8.0, 100.0, 68.0, FALSE, DATE '2026-03-16', NOW()),
  (3, 52.0, 25.0, 9.0, 100.0, 69.0, FALSE, DATE '2026-03-30', NOW()),

  -- COMP3122 Applied Data Science (courseId=4) - on track
  (4, 25.0,  8.0, 1.5, 100.0, 82.0, TRUE,  DATE '2026-02-16', NOW()),
  (4, 40.0, 14.0, 2.0, 100.0, 83.5, TRUE,  DATE '2026-03-02', NOW()),
  (4, 55.0, 22.0, 3.0, 100.0, 81.0, TRUE,  DATE '2026-03-16', NOW()),
  (4, 63.0, 27.0, 4.0, 100.0, 80.5, TRUE,  DATE '2026-03-30', NOW()),

  -- COMP3097 Web Dev Frameworks (courseId=5) - good start
  (5, 22.0,  6.0, 1.0, 100.0, 80.0, TRUE,  DATE '2026-02-16', NOW()),
  (5, 38.0, 12.0, 2.0, 100.0, 81.5, TRUE,  DATE '2026-03-02', NOW()),
  (5, 50.0, 18.0, 3.5, 100.0, 79.0, TRUE,  DATE '2026-03-16', NOW()),
  (5, 60.0, 24.0, 4.0, 100.0, 78.5, TRUE,  DATE '2026-03-30', NOW());
