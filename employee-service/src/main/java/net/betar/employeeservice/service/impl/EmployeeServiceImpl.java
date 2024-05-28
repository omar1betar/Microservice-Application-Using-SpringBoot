package net.betar.employeeservice.service.impl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import net.betar.employeeservice.dto.APIResponseDto;
import net.betar.employeeservice.dto.DepartmentDto;
import net.betar.employeeservice.dto.EmployeeDto;
import net.betar.employeeservice.dto.OrganizationDto;
import net.betar.employeeservice.entity.Employee;
import net.betar.employeeservice.exception.ResourceNotFoundException;
import net.betar.employeeservice.repository.EmployeeRepository;
import net.betar.employeeservice.service.APIClient;
import net.betar.employeeservice.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
//    private APIClient apiClient;
    private WebClient webClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        //using model mapper
        Employee employee = modelMapper.map(employeeDto, Employee.class);

        Employee savedEmployee = employeeRepository.save(employee);
        EmployeeDto employeeDtoResponse = modelMapper.map(savedEmployee, EmployeeDto.class);

        return employeeDtoResponse;
    }


   // @CircuitBreaker(name = "${spring.application.name}",fallbackMethod = "getDefaultDepartment")
   // @Retry(name = "{spring.application.name}",fallbackMethod = "getDefaultDepartment")
    @Override
    public APIResponseDto getEmployeeById(Long id) {
        LOGGER.info("Inside getEmployeeByID, id: ");
        Employee employee = employeeRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Employee","id", id)
        );

        //using model mapper
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);


//        DepartmentDto departmentDto = apiClient.getDepartmentsByCode(employee.getDepartmentCode());

//        employeeDto.setDepartmentCode(departmentDto.getDepartmentCode());
        //organizationCode

        DepartmentDto departmentDto = webClient.get()
                .uri("http://localhost:8771/api/departments/" + employee.getDepartmentCode())
                .retrieve()
                .bodyToMono(DepartmentDto.class)
                .block();


        OrganizationDto organizationDto = webClient.get()
                .uri("http://localhost:8773/api/organization/" + employee.getOrganizationCode())
                .retrieve()
                .bodyToMono(OrganizationDto.class)
                .block();

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);
        apiResponseDto.setOrganizationDto(organizationDto);


        return apiResponseDto;
    }

    public APIResponseDto getDefaultDepartment(Long id ,Exception ex) {

        LOGGER.info("Inside getDefaultDepartment, id: ");

        Employee employee = employeeRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Employee","id", id)
        );

        //using model mapper
        EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setDepartmentName("default");
        departmentDto.setDepartmentDescription("Default");
        departmentDto.setDepartmentCode("0");

        APIResponseDto apiResponseDto = new APIResponseDto();
        apiResponseDto.setEmployee(employeeDto);
        apiResponseDto.setDepartment(departmentDto);


        return apiResponseDto;
    }
}
