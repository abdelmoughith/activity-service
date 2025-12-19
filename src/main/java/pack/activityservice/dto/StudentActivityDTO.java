package pack.activityservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class StudentActivityDTO {

    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotBlank(message = "Course code is required")
    private String courseCode;

    @NotBlank(message = "Module code is required")
    private String moduleCode;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotNull(message = "Sum clicks is required")
    @Min(value = 0, message = "Sum clicks must be greater than or equal to 0")
    private Integer sumClicks;

    // Constructors
    public StudentActivityDTO() {
    }

    public StudentActivityDTO(Long id, Long studentId, String courseCode, String moduleCode, LocalDate date, Integer sumClicks) {
        this.id = id;
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.moduleCode = moduleCode;
        this.date = date;
        this.sumClicks = sumClicks;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getSumClicks() {
        return sumClicks;
    }

    public void setSumClicks(Integer sumClicks) {
        this.sumClicks = sumClicks;
    }
}

