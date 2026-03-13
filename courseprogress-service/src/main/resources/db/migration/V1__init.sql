-- ===============================
-- CourseProgress Service - Initial Schema
-- ===============================

CREATE TABLE IF NOT EXISTS course_progress (
  progress_id BIGSERIAL PRIMARY KEY,
  course_id BIGINT NOT NULL,

  accumulated_percent_points DOUBLE PRECISION NOT NULL,
  used_percent_points DOUBLE PRECISION NOT NULL,
  lost_percent_points DOUBLE PRECISION NOT NULL,
  max_possible_percent DOUBLE PRECISION NOT NULL,
  current_grade_percent DOUBLE PRECISION NOT NULL,

  can_meet_goal BOOLEAN NOT NULL,
  week_of DATE NOT NULL,
  computed_at TIMESTAMP NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_course_progress_course_id
  ON course_progress(course_id);

-- Optional: common query patterns (uncomment if you query by week/course often)
-- CREATE INDEX IF NOT EXISTS idx_course_progress_course_week
--   ON course_progress(course_id, week_of);
