package net.betar.employeeservice.service.impl;

import lombok.AllArgsConstructor;
import net.betar.employeeservice.dto.APIResponseDto;
import net.betar.employeeservice.dto.DepartmentDto;
import net.betar.employeeservice.dto.EmployeeDto;
import net.betar.employeeservice.entity.Employee;
import net.betar.employeeservice.exception.ResourceNotFoundException;
import net.betar.employeeservice.repository.EmployeeRepository;
import net.betar.employeeservice.service.APIClient;
import net.betar.employeeservice.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientRequest;

import java.time.Duration;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;
    @Autowired
    private APIClient apiClient;
//private WebClient webClient;


    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        //using model mapper
        Employee employee = modelMapper.map(employeeDto, Employee.class);

        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDto employeeDtoResponse = modelMapper.map(savedEmployee, EmployeeDto.class);

        return employeeDtoResponse;
    }

    @Override
    public APIResponseDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Employee","id", id)
        );

        //using model mapper
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);


        DepartmentDto departmentDto = apiClient.getDepartmentsByCode(employee.getDepartmentCode());

       // employeeDto.setDepartmentCode(departmentDto.getDepartmentCode());
//        DepartmentDto departmentDto = webClient.get()
//                .uri("http://192.168.69.23:8080/api/departments/" + employee.getDepartmentCode())
//                .retrieve()
//                .bodyToMono(DepartmentDto.class)
//                .block();


        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);


        return apiResponseDto;
    }
}
