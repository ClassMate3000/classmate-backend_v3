-- ===============================
-- Course Service - DEV Seed Data #nezihe
-- ===============================

SET search_path TO course_service;

-- Seed courses only if table is empty (idempotent)
DO $$
DECLARE
  c1_id BIGINT;
  c2_id BIGINT;
  c3_id BIGINT;
  c4_id BIGINT;
  c5_id BIGINT;
BEGIN
  IF NOT EXISTS (SELECT 1 FROM courses) THEN

    -- Insert 5 sample courses
    INSERT INTO courses (code, title, instructor, grade_goal, start_week)
    VALUES
      ('COMP3095', 'Capstone Project I',             'J. Silveira', 85, DATE '2026-01-13'),
      ('COMP3133', 'Full Stack Development II',       'A. Patel',    80, DATE '2026-01-13'),
      ('COMP3078', 'Mobile Application Development', 'S. Kim',       75, DATE '2026-01-13'),
      ('COMP3122', 'Applied Data Science',           'M. Torres',    80, DATE '2026-01-13'),
      ('COMP3097', 'Web Development Frameworks',     'R. Chen',      78, DATE '2026-01-13');

    -- Get their generated ids (stable by code)
    SELECT id INTO c1_id FROM courses WHERE code = 'COMP3095';
    SELECT id INTO c2_id FROM courses WHERE code = 'COMP3133';
    SELECT id INTO c3_id FROM courses WHERE code = 'COMP3078';
    SELECT id INTO c4_id FROM courses WHERE code = 'COMP3122';
    SELECT id INTO c5_id FROM courses WHERE code = 'COMP3097';

    -- Meetings for COMP3095 (Mon/Wed 10:00-12:00)
    INSERT INTO course_meetings (course_id, day_of_week, start_time, end_time)
    VALUES
      (c1_id, 1, TIME '10:00', TIME '12:00'),
      (c1_id, 3, TIME '10:00', TIME '12:00');

    -- Meetings for COMP3133 (Tue/Thu 09:00-11:00)
    INSERT INTO course_meetings (course_id, day_of_week, start_time, end_time)
    VALUES
      (c2_id, 2, TIME '09:00', TIME '11:00'),
      (c2_id, 4, TIME '09:00', TIME '11:00');

    -- Meetings for COMP3078 (Tue/Fri 13:00-15:00)
    INSERT INTO course_meetings (course_id, day_of_week, start_time, end_time)
    VALUES
      (c3_id, 2, TIME '13:00', TIME '15:00'),
      (c3_id, 5, TIME '13:00', TIME '15:00');

    -- Meetings for COMP3122 (Mon/Wed 14:00-16:00)
    INSERT INTO course_meetings (course_id, day_of_week, start_time, end_time)
    VALUES
      (c4_id, 1, TIME '14:00', TIME '16:00'),
      (c4_id, 3, TIME '14:00', TIME '16:00');

    -- Meetings for COMP3097 (Thu 09:00-13:00)
    INSERT INTO course_meetings (course_id, day_of_week, start_time, end_time)
    VALUES
      (c5_id, 4, TIME '09:00', TIME '13:00');

  END IF;
END $$;