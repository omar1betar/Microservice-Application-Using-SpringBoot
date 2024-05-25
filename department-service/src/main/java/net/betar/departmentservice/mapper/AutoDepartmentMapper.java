package net.betar.departmentservice.mapper;

import net.betar.departmentservice.dto.DepartmentDto;
import net.betar.departmentservice.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AutoDepartmentMapper {

    AutoDepartmentMapper MAPPER = Mappers.getMapper(AutoDepartmentMapper.class);

    DepartmentDto maptoDepartmentDto(Department department);
    Department maptoDepartment(DepartmentDto departmentDto);
}
