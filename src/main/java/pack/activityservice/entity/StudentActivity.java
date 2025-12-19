package pack.activityservice.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "student_activity",
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_code", "module_code", "date"}))
public class StudentActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "course_code", nullable = false, length = 50)
    private String courseCode;

    @Column(name = "module_code", nullable = false, length = 50)
    private String moduleCode;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "sum_clicks", nullable = false)
    private Integer sumClicks;

    // Constructors
    public StudentActivity() {
    }

    public StudentActivity(Long id, Long studentId, String courseCode, String moduleCode, LocalDate date, Integer sumClicks) {
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

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentActivity that = (StudentActivity) o;
        return Objects.equals(id, that.id) &&
               Objects.equals(studentId, that.studentId) &&
               Objects.equals(courseCode, that.courseCode) &&
               Objects.equals(moduleCode, that.moduleCode) &&
               Objects.equals(date, that.date) &&
               Objects.equals(sumClicks, that.sumClicks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentId, courseCode, moduleCode, date, sumClicks);
    }

    // toString
    @Override
    public String toString() {
        return "StudentActivity{" +
                "id=" + id +
                ", studentId=" + studentId +
                ", courseCode='" + courseCode + '\'' +
                ", moduleCode='" + moduleCode + '\'' +
                ", date=" + date +
                ", sumClicks=" + sumClicks +
                '}';
    }
}

