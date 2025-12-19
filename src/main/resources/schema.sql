-- Create database for activity-service
CREATE DATABASE IF NOT EXISTS activity_service_db;

USE activity_service_db;

-- Create student_activity table
CREATE TABLE IF NOT EXISTS student_activity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id BIGINT NOT NULL,
    course_code VARCHAR(50) NOT NULL,
    module_code VARCHAR(50) NOT NULL,
    date DATE NOT NULL,
    sum_clicks INT NOT NULL DEFAULT 0,
    CONSTRAINT uk_student_activity UNIQUE (student_id, course_code, module_code, date),
    INDEX idx_student_id (student_id),
    INDEX idx_course (course_code, module_code),
    INDEX idx_date (date),
    INDEX idx_student_course (student_id, course_code, module_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Sample data for testing (optional)
INSERT INTO student_activity (student_id, course_code, module_code, date, sum_clicks)
VALUES
    (1, 'AAA', '2013J', '2024-01-15', 25),
    (1, 'AAA', '2013J', '2024-01-16', 30),
    (1, 'BBB', '2013J', '2024-01-15', 15),
    (2, 'AAA', '2013J', '2024-01-15', 40),
    (2, 'AAA', '2013J', '2024-01-16', 35)
ON DUPLICATE KEY UPDATE sum_clicks = VALUES(sum_clicks);

