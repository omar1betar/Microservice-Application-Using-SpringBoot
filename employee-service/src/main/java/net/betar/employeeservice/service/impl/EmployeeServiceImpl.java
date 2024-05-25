package net.betar.employeeservice.service.impl;

import lombok.AllArgsConstructor;
import net.betar.employeeservice.dto.EmployeeDto;
import net.betar.employeeservice.entity.Employee;
import net.betar.employeeservice.exception.ResourceNotFoundException;
import net.betar.employeeservice.repository.EmployeeRepository;
import net.betar.employeeservice.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;


    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        //using model mapper
        Employee employee = modelMapper.map(employeeDto, Employee.class);

        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDto employeeDtoResponse = modelMapper.map(savedEmployee, EmployeeDto.class);

        return employeeDtoResponse;
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Employee","id", id)
        );
        //using model mapper
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

        return employeeDto;
    }
}
