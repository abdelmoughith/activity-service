package pack.activityservice.service;

import pack.activityservice.dto.StudentActivityCreateDTO;
import pack.activityservice.dto.StudentActivityDTO;
import pack.activityservice.dto.StudentActivityUpdateDTO;

import java.time.LocalDate;
import java.util.List;

public interface StudentActivityService {

    StudentActivityDTO createActivity(StudentActivityCreateDTO createDTO);

    StudentActivityDTO getActivityById(Long id);

    List<StudentActivityDTO> getAllActivities();

    List<StudentActivityDTO> getActivitiesByStudentId(Long studentId);

    List<StudentActivityDTO> getActivitiesByCourse(String courseCode, String moduleCode);

    List<StudentActivityDTO> getActivitiesByStudentAndCourse(
            Long studentId, String courseCode, String moduleCode);

    List<StudentActivityDTO> getActivitiesByStudentAndDateRange(
            Long studentId, LocalDate startDate, LocalDate endDate);

    List<StudentActivityDTO> getActivitiesByCourseAndDateRange(
            String courseCode, String moduleCode, LocalDate startDate, LocalDate endDate);

    StudentActivityDTO updateActivity(Long id, StudentActivityUpdateDTO updateDTO);

    void deleteActivity(Long id);

    Long getTotalClicksByStudent(Long studentId);

    Long getTotalClicksByStudentAndCourse(Long studentId, String courseCode, String moduleCode);

    StudentActivityDTO incrementClicks(Long studentId, String courseCode, String moduleCode, LocalDate date, Integer clicks);
}

