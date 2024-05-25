package net.betar.departmentservice.service.impl;

import lombok.AllArgsConstructor;
import net.betar.departmentservice.dto.DepartmentDto;
import net.betar.departmentservice.entity.Department;
import net.betar.departmentservice.mapper.AutoDepartmentMapper;
import net.betar.departmentservice.repository.DepartmentRepository;
import net.betar.departmentservice.service.DepartmentService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        //convert department dto to department entity
        Department department = AutoDepartmentMapper.MAPPER.maptoDepartment(departmentDto);
//        Department department = new Department(
//                departmentDto.getId(),
//                departmentDto.getDepartmentName(),
//                departmentDto.getDepartmentDescription(),
//                departmentDto.getDepartmentCode()
//        );
        Department savedDepartment =departmentRepository.save(department);
        DepartmentDto departmentDtoReturn = AutoDepartmentMapper.MAPPER.maptoDepartmentDto(savedDepartment);
//        DepartmentDto departmentDtoReturn = new DepartmentDto(
//                savedDepartment.getId(),
//                savedDepartment.getDepartmentName(),
//                savedDepartment.getDepartmentDescription(),
//                savedDepartment.getDepartmentCode()
//        );
        return departmentDtoReturn;
    }

    @Override
    public DepartmentDto getDepartmentByCode(String departmentCode) {
        Department department = departmentRepository.findByDepartmentCode(departmentCode);
        //using mapstruct
        DepartmentDto departmentDtoReturn = AutoDepartmentMapper.MAPPER.maptoDepartmentDto(department);
//        DepartmentDto departmentDtoReturn = new DepartmentDto(
//                department.getId(),
//                department.getDepartmentName(),
//                department.getDepartmentDescription(),
//                department.getDepartmentCode()
//        );
        return departmentDtoReturn;
    }
}
