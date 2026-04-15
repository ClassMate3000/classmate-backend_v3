-- ===============================
-- Course Service - Update Seed Data
-- ===============================

SET search_path TO course_service;

-- Clear existing data and re-seed with updated courses
DELETE FROM course_meetings;
DELETE FROM courses;

-- Insert updated courses
INSERT INTO courses (code, title, instructor, grade_goal, start_week)
VALUES
  ('COMP3095', 'Capstone Project I',             'J. Silveira', 85, DATE '2026-01-13'),
  ('COMP3133', 'Full Stack Development II',       'A. Patel',    80, DATE '2026-01-13'),
  ('COMP3078', 'Mobile Application Development', 'S. Kim',       75, DATE '2026-01-13'),
  ('COMP3122', 'Applied Data Science',           'M. Torres',    80, DATE '2026-01-13'),
  ('COMP3097', 'Web Development Frameworks',     'R. Chen',      78, DATE '2026-01-13');

-- Insert meetings
INSERT INTO course_meetings (course_id, day_of_week, start_time, end_time)
VALUES
  ((SELECT id FROM courses WHERE code = 'COMP3095'), 1, TIME '10:00', TIME '12:00'),
  ((SELECT id FROM courses WHERE code = 'COMP3095'), 3, TIME '10:00', TIME '12:00'),
  ((SELECT id FROM courses WHERE code = 'COMP3133'), 2, TIME '09:00', TIME '11:00'),
  ((SELECT id FROM courses WHERE code = 'COMP3133'), 4, TIME '09:00', TIME '11:00'),
  ((SELECT id FROM courses WHERE code = 'COMP3078'), 2, TIME '13:00', TIME '15:00'),
  ((SELECT id FROM courses WHERE code = 'COMP3078'), 5, TIME '13:00', TIME '15:00'),
  ((SELECT id FROM courses WHERE code = 'COMP3122'), 1, TIME '14:00', TIME '16:00'),
  ((SELECT id FROM courses WHERE code = 'COMP3122'), 3, TIME '14:00', TIME '16:00'),
  ((SELECT id FROM courses WHERE code = 'COMP3097'), 4, TIME '09:00', TIME '13:00');
