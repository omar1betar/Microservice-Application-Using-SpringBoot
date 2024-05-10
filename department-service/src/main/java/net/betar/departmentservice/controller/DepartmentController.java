package net.betar.departmentservice.controller;

import lombok.AllArgsConstructor;
import net.betar.departmentservice.dto.DepartmentDto;
import net.betar.departmentservice.entity.Department;
import net.betar.departmentservice.service.DepartmentService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "api/departments")
@AllArgsConstructor
public class DepartmentController {

    private DepartmentService departmentService;
    //build save dep rest api
    @PostMapping
    public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody DepartmentDto departmentDto){

        DepartmentDto savedDepartment = departmentService.saveDepartment(departmentDto);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

    //get department by code
    @GetMapping("{department-code}")
    public ResponseEntity<DepartmentDto> getDepartmentsByCode(@PathVariable("department-code") String departmentCode){

        DepartmentDto department = departmentService.getDepartmentByCode(departmentCode);

        return new ResponseEntity<>(department, HttpStatus.OK);
    }
}
