package pack.activityservice.dto;

import jakarta.validation.constraints.Min;

import java.time.LocalDate;

public class StudentActivityUpdateDTO {

    private LocalDate date;

    @Min(value = 0, message = "Sum clicks must be greater than or equal to 0")
    private Integer sumClicks;

    // Constructors
    public StudentActivityUpdateDTO() {
    }

    public StudentActivityUpdateDTO(LocalDate date, Integer sumClicks) {
        this.date = date;
        this.sumClicks = sumClicks;
    }

    // Getters and Setters
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

