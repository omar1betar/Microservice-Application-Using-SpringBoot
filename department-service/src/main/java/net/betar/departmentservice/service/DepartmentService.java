package net.betar.departmentservice.service;

import net.betar.departmentservice.dto.DepartmentDto;
import net.betar.departmentservice.repository.DepartmentRepository;

public interface DepartmentService {

    DepartmentDto saveDepartment(DepartmentDto departmentDto);
    DepartmentDto getDepartmentByCode(String departmentCode);

}
