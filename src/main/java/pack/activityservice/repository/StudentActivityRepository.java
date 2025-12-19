package pack.activityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pack.activityservice.entity.StudentActivity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentActivityRepository extends JpaRepository<StudentActivity, Long> {

    // Find all activities for a specific student
    List<StudentActivity> findByStudentId(Long studentId);

    // Find all activities for a specific course
    List<StudentActivity> findByCourseCodeAndModuleCode(String courseCode, String moduleCode);

    // Find activities for a student in a specific course
    List<StudentActivity> findByStudentIdAndCourseCodeAndModuleCode(
            Long studentId, String courseCode, String moduleCode);

    // Find activities for a student on a specific date
    List<StudentActivity> findByStudentIdAndDate(Long studentId, LocalDate date);

    // Find activities for a student between dates
    List<StudentActivity> findByStudentIdAndDateBetween(
            Long studentId, LocalDate startDate, LocalDate endDate);

    // Find activities for a course between dates
    List<StudentActivity> findByCourseCodeAndModuleCodeAndDateBetween(
            String courseCode, String moduleCode, LocalDate startDate, LocalDate endDate);

    // Check if activity exists for student on a specific date and course
    Optional<StudentActivity> findByStudentIdAndCourseCodeAndModuleCodeAndDate(
            Long studentId, String courseCode, String moduleCode, LocalDate date);

    // Get total clicks for a student
    @Query("SELECT SUM(sa.sumClicks) FROM StudentActivity sa WHERE sa.studentId = :studentId")
    Long getTotalClicksByStudentId(@Param("studentId") Long studentId);

    // Get total clicks for a student in a specific course
    @Query("SELECT SUM(sa.sumClicks) FROM StudentActivity sa WHERE sa.studentId = :studentId " +
           "AND sa.courseCode = :courseCode AND sa.moduleCode = :moduleCode")
    Long getTotalClicksByStudentIdAndCourse(
            @Param("studentId") Long studentId,
            @Param("courseCode") String courseCode,
            @Param("moduleCode") String moduleCode);
}

