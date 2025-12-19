package pack.activityservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pack.activityservice.dto.StudentActivityCreateDTO;
import pack.activityservice.dto.StudentActivityDTO;
import pack.activityservice.dto.StudentActivityUpdateDTO;
import pack.activityservice.entity.StudentActivity;
import pack.activityservice.exception.ResourceNotFoundException;
import pack.activityservice.repository.StudentActivityRepository;
import pack.activityservice.service.impl.StudentActivityServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentActivityServiceTest {

    @Mock
    private StudentActivityRepository studentActivityRepository;

    @InjectMocks
    private StudentActivityServiceImpl studentActivityService;

    private StudentActivity studentActivity;
    private StudentActivityCreateDTO createDTO;
    private StudentActivityUpdateDTO updateDTO;

    @BeforeEach
    void setUp() {
        studentActivity = new StudentActivity();
        studentActivity.setId(1L);
        studentActivity.setStudentId(123L);
        studentActivity.setCourseCode("AAA");
        studentActivity.setModuleCode("2013J");
        studentActivity.setDate(LocalDate.of(2024, 1, 15));
        studentActivity.setSumClicks(25);

        createDTO = new StudentActivityCreateDTO();
        createDTO.setStudentId(123L);
        createDTO.setCourseCode("AAA");
        createDTO.setModuleCode("2013J");
        createDTO.setDate(LocalDate.of(2024, 1, 15));
        createDTO.setSumClicks(25);

        updateDTO = new StudentActivityUpdateDTO();
        updateDTO.setSumClicks(30);
    }

    @Test
    void testCreateActivity_Success() {
        when(studentActivityRepository.save(any(StudentActivity.class))).thenReturn(studentActivity);

        StudentActivityDTO result = studentActivityService.createActivity(createDTO);

        assertNotNull(result);
        assertEquals(studentActivity.getId(), result.getId());
        assertEquals(studentActivity.getStudentId(), result.getStudentId());
        assertEquals(studentActivity.getCourseCode(), result.getCourseCode());
        assertEquals(studentActivity.getSumClicks(), result.getSumClicks());
        verify(studentActivityRepository, times(1)).save(any(StudentActivity.class));
    }

    @Test
    void testGetActivityById_Success() {
        when(studentActivityRepository.findById(1L)).thenReturn(Optional.of(studentActivity));

        StudentActivityDTO result = studentActivityService.getActivityById(1L);

        assertNotNull(result);
        assertEquals(studentActivity.getId(), result.getId());
        verify(studentActivityRepository, times(1)).findById(1L);
    }

    @Test
    void testGetActivityById_NotFound() {
        when(studentActivityRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            studentActivityService.getActivityById(1L);
        });

        verify(studentActivityRepository, times(1)).findById(1L);
    }

    @Test
    void testGetActivitiesByStudentId() {
        List<StudentActivity> activities = Arrays.asList(studentActivity);
        when(studentActivityRepository.findByStudentId(123L)).thenReturn(activities);

        List<StudentActivityDTO> result = studentActivityService.getActivitiesByStudentId(123L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(studentActivity.getStudentId(), result.get(0).getStudentId());
        verify(studentActivityRepository, times(1)).findByStudentId(123L);
    }

    @Test
    void testGetActivitiesByCourse() {
        List<StudentActivity> activities = Arrays.asList(studentActivity);
        when(studentActivityRepository.findByCourseCodeAndModuleCode("AAA", "2013J"))
                .thenReturn(activities);

        List<StudentActivityDTO> result = studentActivityService.getActivitiesByCourse("AAA", "2013J");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(studentActivity.getCourseCode(), result.get(0).getCourseCode());
        verify(studentActivityRepository, times(1)).findByCourseCodeAndModuleCode("AAA", "2013J");
    }

    @Test
    void testUpdateActivity_Success() {
        when(studentActivityRepository.findById(1L)).thenReturn(Optional.of(studentActivity));
        when(studentActivityRepository.save(any(StudentActivity.class))).thenReturn(studentActivity);

        StudentActivityDTO result = studentActivityService.updateActivity(1L, updateDTO);

        assertNotNull(result);
        verify(studentActivityRepository, times(1)).findById(1L);
        verify(studentActivityRepository, times(1)).save(any(StudentActivity.class));
    }

    @Test
    void testDeleteActivity_Success() {
        when(studentActivityRepository.existsById(1L)).thenReturn(true);
        doNothing().when(studentActivityRepository).deleteById(1L);

        studentActivityService.deleteActivity(1L);

        verify(studentActivityRepository, times(1)).existsById(1L);
        verify(studentActivityRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteActivity_NotFound() {
        when(studentActivityRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> {
            studentActivityService.deleteActivity(1L);
        });

        verify(studentActivityRepository, times(1)).existsById(1L);
        verify(studentActivityRepository, never()).deleteById(1L);
    }

    @Test
    void testGetTotalClicksByStudent() {
        when(studentActivityRepository.getTotalClicksByStudentId(123L)).thenReturn(100L);

        Long result = studentActivityService.getTotalClicksByStudent(123L);

        assertEquals(100L, result);
        verify(studentActivityRepository, times(1)).getTotalClicksByStudentId(123L);
    }

    @Test
    void testGetTotalClicksByStudentAndCourse() {
        when(studentActivityRepository.getTotalClicksByStudentIdAndCourse(123L, "AAA", "2013J"))
                .thenReturn(50L);

        Long result = studentActivityService.getTotalClicksByStudentAndCourse(123L, "AAA", "2013J");

        assertEquals(50L, result);
        verify(studentActivityRepository, times(1))
                .getTotalClicksByStudentIdAndCourse(123L, "AAA", "2013J");
    }

    @Test
    void testIncrementClicks_ExistingActivity() {
        LocalDate date = LocalDate.of(2024, 1, 15);
        when(studentActivityRepository.findByStudentIdAndCourseCodeAndModuleCodeAndDate(
                123L, "AAA", "2013J", date))
                .thenReturn(Optional.of(studentActivity));
        when(studentActivityRepository.save(any(StudentActivity.class))).thenReturn(studentActivity);

        StudentActivityDTO result = studentActivityService.incrementClicks(123L, "AAA", "2013J", date, 5);

        assertNotNull(result);
        verify(studentActivityRepository, times(1))
                .findByStudentIdAndCourseCodeAndModuleCodeAndDate(123L, "AAA", "2013J", date);
        verify(studentActivityRepository, times(1)).save(any(StudentActivity.class));
    }

    @Test
    void testIncrementClicks_NewActivity() {
        LocalDate date = LocalDate.of(2024, 1, 15);
        when(studentActivityRepository.findByStudentIdAndCourseCodeAndModuleCodeAndDate(
                123L, "AAA", "2013J", date))
                .thenReturn(Optional.empty());
        when(studentActivityRepository.save(any(StudentActivity.class))).thenReturn(studentActivity);

        StudentActivityDTO result = studentActivityService.incrementClicks(123L, "AAA", "2013J", date, 5);

        assertNotNull(result);
        verify(studentActivityRepository, times(1))
                .findByStudentIdAndCourseCodeAndModuleCodeAndDate(123L, "AAA", "2013J", date);
        verify(studentActivityRepository, times(1)).save(any(StudentActivity.class));
    }
}

