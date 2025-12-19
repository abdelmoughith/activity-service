package pack.activityservice.mapper;

import pack.activityservice.dto.StudentActivityCreateDTO;
import pack.activityservice.dto.StudentActivityDTO;
import pack.activityservice.dto.StudentActivityUpdateDTO;
import pack.activityservice.entity.StudentActivity;

public class StudentActivityMapper {

    public static StudentActivityDTO toDTO(StudentActivity entity) {
        if (entity == null) {
            return null;
        }
        StudentActivityDTO dto = new StudentActivityDTO();
        dto.setId(entity.getId());
        dto.setStudentId(entity.getStudentId());
        dto.setCourseCode(entity.getCourseCode());
        dto.setModuleCode(entity.getModuleCode());
        dto.setDate(entity.getDate());
        dto.setSumClicks(entity.getSumClicks());
        return dto;
    }

    public static StudentActivity toEntity(StudentActivityCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        StudentActivity entity = new StudentActivity();
        entity.setStudentId(dto.getStudentId());
        entity.setCourseCode(dto.getCourseCode());
        entity.setModuleCode(dto.getModuleCode());
        entity.setDate(dto.getDate());
        entity.setSumClicks(dto.getSumClicks());
        return entity;
    }

    public static void updateEntityFromDTO(StudentActivity entity, StudentActivityUpdateDTO dto) {
        if (dto.getDate() != null) {
            entity.setDate(dto.getDate());
        }
        if (dto.getSumClicks() != null) {
            entity.setSumClicks(dto.getSumClicks());
        }
    }
}

