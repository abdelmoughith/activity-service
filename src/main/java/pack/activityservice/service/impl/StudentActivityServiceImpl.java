package pack.activityservice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pack.activityservice.dto.StudentActivityCreateDTO;
import pack.activityservice.dto.StudentActivityDTO;
import pack.activityservice.dto.StudentActivityUpdateDTO;
import pack.activityservice.entity.StudentActivity;
import pack.activityservice.exception.ResourceNotFoundException;
import pack.activityservice.mapper.StudentActivityMapper;
import pack.activityservice.repository.StudentActivityRepository;
import pack.activityservice.service.StudentActivityService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentActivityServiceImpl implements StudentActivityService {

    private static final Logger log = LoggerFactory.getLogger(StudentActivityServiceImpl.class);

    private final StudentActivityRepository studentActivityRepository;

    public StudentActivityServiceImpl(StudentActivityRepository studentActivityRepository) {
        this.studentActivityRepository = studentActivityRepository;
    }

    @Override
    public StudentActivityDTO createActivity(StudentActivityCreateDTO createDTO) {
        log.info("Creating new student activity for student ID: {}", createDTO.getStudentId());

        StudentActivity activity = StudentActivityMapper.toEntity(createDTO);
        StudentActivity savedActivity = studentActivityRepository.save(activity);

        log.info("Successfully created student activity with ID: {}", savedActivity.getId());
        return StudentActivityMapper.toDTO(savedActivity);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentActivityDTO getActivityById(Long id) {
        log.info("Fetching student activity with ID: {}", id);

        StudentActivity activity = studentActivityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student activity not found with ID: " + id));

        return StudentActivityMapper.toDTO(activity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentActivityDTO> getAllActivities() {
        log.info("Fetching all student activities");

        return studentActivityRepository.findAll().stream()
                .map(StudentActivityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentActivityDTO> getActivitiesByStudentId(Long studentId) {
        log.info("Fetching activities for student ID: {}", studentId);

        return studentActivityRepository.findByStudentId(studentId).stream()
                .map(StudentActivityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentActivityDTO> getActivitiesByCourse(String courseCode, String moduleCode) {
        log.info("Fetching activities for course: {}-{}", courseCode, moduleCode);

        return studentActivityRepository.findByCourseCodeAndModuleCode(courseCode, moduleCode).stream()
                .map(StudentActivityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentActivityDTO> getActivitiesByStudentAndCourse(
            Long studentId, String courseCode, String moduleCode) {
        log.info("Fetching activities for student ID: {} in course: {}-{}",
                studentId, courseCode, moduleCode);

        return studentActivityRepository
                .findByStudentIdAndCourseCodeAndModuleCode(studentId, courseCode, moduleCode).stream()
                .map(StudentActivityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentActivityDTO> getActivitiesByStudentAndDateRange(
            Long studentId, LocalDate startDate, LocalDate endDate) {
        log.info("Fetching activities for student ID: {} between {} and {}",
                studentId, startDate, endDate);

        return studentActivityRepository
                .findByStudentIdAndDateBetween(studentId, startDate, endDate).stream()
                .map(StudentActivityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentActivityDTO> getActivitiesByCourseAndDateRange(
            String courseCode, String moduleCode, LocalDate startDate, LocalDate endDate) {
        log.info("Fetching activities for course: {}-{} between {} and {}",
                courseCode, moduleCode, startDate, endDate);

        return studentActivityRepository
                .findByCourseCodeAndModuleCodeAndDateBetween(courseCode, moduleCode, startDate, endDate).stream()
                .map(StudentActivityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentActivityDTO updateActivity(Long id, StudentActivityUpdateDTO updateDTO) {
        log.info("Updating student activity with ID: {}", id);

        StudentActivity activity = studentActivityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student activity not found with ID: " + id));

        StudentActivityMapper.updateEntityFromDTO(activity, updateDTO);
        StudentActivity updatedActivity = studentActivityRepository.save(activity);

        log.info("Successfully updated student activity with ID: {}", id);
        return StudentActivityMapper.toDTO(updatedActivity);
    }

    @Override
    public void deleteActivity(Long id) {
        log.info("Deleting student activity with ID: {}", id);

        if (!studentActivityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student activity not found with ID: " + id);
        }

        studentActivityRepository.deleteById(id);
        log.info("Successfully deleted student activity with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getTotalClicksByStudent(Long studentId) {
        log.info("Calculating total clicks for student ID: {}", studentId);

        Long totalClicks = studentActivityRepository.getTotalClicksByStudentId(studentId);
        return totalClicks != null ? totalClicks : 0L;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getTotalClicksByStudentAndCourse(Long studentId, String courseCode, String moduleCode) {
        log.info("Calculating total clicks for student ID: {} in course: {}-{}",
                studentId, courseCode, moduleCode);

        Long totalClicks = studentActivityRepository
                .getTotalClicksByStudentIdAndCourse(studentId, courseCode, moduleCode);
        return totalClicks != null ? totalClicks : 0L;
    }

    @Override
    public StudentActivityDTO incrementClicks(Long studentId, String courseCode,
                                              String moduleCode, LocalDate date, Integer clicks) {
        log.info("Incrementing clicks for student ID: {} on date: {}", studentId, date);

        // Try to find existing activity for this student, course, and date
        StudentActivity activity = studentActivityRepository
                .findByStudentIdAndCourseCodeAndModuleCodeAndDate(studentId, courseCode, moduleCode, date)
                .orElseGet(() -> {
                    // Create new activity if not exists
                    log.info("Creating new activity record for student ID: {} on date: {}", studentId, date);
                    StudentActivity newActivity = new StudentActivity();
                    newActivity.setStudentId(studentId);
                    newActivity.setCourseCode(courseCode);
                    newActivity.setModuleCode(moduleCode);
                    newActivity.setDate(date);
                    newActivity.setSumClicks(0);
                    return newActivity;
                });

        // Increment clicks
        activity.setSumClicks(activity.getSumClicks() + clicks);
        StudentActivity savedActivity = studentActivityRepository.save(activity);

        log.info("Successfully incremented clicks for activity ID: {}", savedActivity.getId());
        return StudentActivityMapper.toDTO(savedActivity);
    }
}

