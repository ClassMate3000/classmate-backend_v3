-- ===============================
-- Course Service - DEV Seed Data #nezihe
-- ===============================

SET search_path TO course_service;

-- Seed courses only if table is empty (idempotent)
DO $$
DECLARE
  c1_id BIGINT;
  c2_id BIGINT;
BEGIN
  IF NOT EXISTS (SELECT 1 FROM courses) THEN

    -- Insert 2 sample courses
    INSERT INTO courses (code, title, instructor, grade_goal, start_week)
    VALUES
      ('COMP3095', 'Capstone Project I', 'GBC', 85, DATE '2026-02-10'),
      ('COMP3133', 'Full Stack Development II', 'GBC', 80, DATE '2026-02-10');

    -- Get their generated ids (stable by code)
    SELECT id INTO c1_id FROM courses WHERE code = 'COMP3095';
    SELECT id INTO c2_id FROM courses WHERE code = 'COMP3133';

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

  END IF;
END $$;
