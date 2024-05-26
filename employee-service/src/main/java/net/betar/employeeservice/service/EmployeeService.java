package net.betar.employeeservice.service;

import net.betar.employeeservice.dto.APIResponseDto;
import net.betar.employeeservice.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto saveEmployee(EmployeeDto employeeDto);

    APIResponseDto getEmployeeById(Long id);
}
