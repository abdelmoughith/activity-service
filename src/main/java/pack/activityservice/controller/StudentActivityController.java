package pack.activityservice.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pack.activityservice.dto.StudentActivityCreateDTO;
import pack.activityservice.dto.StudentActivityDTO;
import pack.activityservice.dto.StudentActivityUpdateDTO;
import pack.activityservice.service.StudentActivityService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class StudentActivityController {

    private final StudentActivityService studentActivityService;

    public StudentActivityController(StudentActivityService studentActivityService) {
        this.studentActivityService = studentActivityService;
    }

    @PostMapping
    public ResponseEntity<StudentActivityDTO> createActivity(@Valid @RequestBody StudentActivityCreateDTO createDTO) {
        StudentActivityDTO createdActivity = studentActivityService.createActivity(createDTO);
        return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentActivityDTO> getActivityById(@PathVariable Long id) {
        StudentActivityDTO activity = studentActivityService.getActivityById(id);
        return ResponseEntity.ok(activity);
    }

    @GetMapping
    public ResponseEntity<List<StudentActivityDTO>> getAllActivities() {
        List<StudentActivityDTO> activities = studentActivityService.getAllActivities();
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentActivityDTO>> getActivitiesByStudentId(@PathVariable Long studentId) {
        List<StudentActivityDTO> activities = studentActivityService.getActivitiesByStudentId(studentId);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/course/{courseCode}/{moduleCode}")
    public ResponseEntity<List<StudentActivityDTO>> getActivitiesByCourse(
            @PathVariable String courseCode,
            @PathVariable String moduleCode) {
        List<StudentActivityDTO> activities = studentActivityService.getActivitiesByCourse(courseCode, moduleCode);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/student/{studentId}/course/{courseCode}/{moduleCode}")
    public ResponseEntity<List<StudentActivityDTO>> getActivitiesByStudentAndCourse(
            @PathVariable Long studentId,
            @PathVariable String courseCode,
            @PathVariable String moduleCode) {
        List<StudentActivityDTO> activities = studentActivityService.getActivitiesByStudentAndCourse(studentId, courseCode, moduleCode);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/student/{studentId}/date-range")
    public ResponseEntity<List<StudentActivityDTO>> getActivitiesByStudentAndDateRange(
            @PathVariable Long studentId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<StudentActivityDTO> activities = studentActivityService.getActivitiesByStudentAndDateRange(studentId, startDate, endDate);
        return ResponseEntity.ok(activities);
    }

    @GetMapping("/course/{courseCode}/{moduleCode}/date-range")
    public ResponseEntity<List<StudentActivityDTO>> getActivitiesByCourseAndDateRange(
            @PathVariable String courseCode,
            @PathVariable String moduleCode,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<StudentActivityDTO> activities = studentActivityService.getActivitiesByCourseAndDateRange(courseCode, moduleCode, startDate, endDate);
        return ResponseEntity.ok(activities);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentActivityDTO> updateActivity(
            @PathVariable Long id,
            @Valid @RequestBody StudentActivityUpdateDTO updateDTO) {
        StudentActivityDTO updatedActivity = studentActivityService.updateActivity(id, updateDTO);
        return ResponseEntity.ok(updatedActivity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        studentActivityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}/total-clicks")
    public ResponseEntity<Long> getTotalClicksByStudent(@PathVariable Long studentId) {
        Long totalClicks = studentActivityService.getTotalClicksByStudent(studentId);
        return ResponseEntity.ok(totalClicks);
    }

    @GetMapping("/student/{studentId}/course/{courseCode}/{moduleCode}/total-clicks")
    public ResponseEntity<Long> getTotalClicksByStudentAndCourse(
            @PathVariable Long studentId,
            @PathVariable String courseCode,
            @PathVariable String moduleCode) {
        Long totalClicks = studentActivityService.getTotalClicksByStudentAndCourse(studentId, courseCode, moduleCode);
        return ResponseEntity.ok(totalClicks);
    }

    @PostMapping("/increment")
    public ResponseEntity<StudentActivityDTO> incrementClicks(
            @RequestParam Long studentId,
            @RequestParam String courseCode,
            @RequestParam String moduleCode,
            @RequestParam LocalDate date,
            @RequestParam(defaultValue = "1") Integer clicks) {
        StudentActivityDTO activity = studentActivityService.incrementClicks(studentId, courseCode, moduleCode, date, clicks);
        return ResponseEntity.ok(activity);
    }
}

