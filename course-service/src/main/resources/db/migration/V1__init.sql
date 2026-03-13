-- ===============================
-- Course Service - Initial Schema
-- ===============================

CREATE SCHEMA IF NOT EXISTS course_service;
SET search_path TO course_service;

CREATE TABLE IF NOT EXISTS courses (
  id          BIGSERIAL PRIMARY KEY,
  code        VARCHAR(10)  NOT NULL,
  title       TEXT         NOT NULL,
  instructor  TEXT         NOT NULL,
  grade_goal  INTEGER      NOT NULL,
  start_week  DATE         NOT NULL
);

-- Meetings for each course (multiple rows per course)
-- day_of_week: 1=Monday ... 7=Sunday (simple + portable)
CREATE TABLE IF NOT EXISTS course_meetings (
  course_id   BIGINT   NOT NULL,
  day_of_week INTEGER NOT NULL CHECK (day_of_week BETWEEN 1 AND 7),
  start_time  TIME     NOT NULL,
  end_time    TIME     NOT NULL,
  CONSTRAINT fk_course_meetings_course
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
  CONSTRAINT chk_course_meetings_time
    CHECK (end_time > start_time),
  CONSTRAINT pk_course_meetings
    PRIMARY KEY (course_id, day_of_week, start_time, end_time)
);

CREATE INDEX IF NOT EXISTS idx_course_meetings_course_id
  ON course_meetings(course_id);
