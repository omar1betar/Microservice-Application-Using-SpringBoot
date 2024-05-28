package net.betar.organizationservice.controller;

import lombok.AllArgsConstructor;
import net.betar.organizationservice.dto.OrganizationDto;
import net.betar.organizationservice.service.OrganizationService;
import org.hibernate.annotations.GeneratorType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/organization")
@AllArgsConstructor
public class OrganizationController {

    private OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<OrganizationDto> saveOrganization (@RequestBody OrganizationDto organizationDto){
        OrganizationDto savedOrganization = organizationService.saveOrganization(organizationDto);

        return new ResponseEntity<>(savedOrganization, HttpStatus.CREATED);
    }
    @GetMapping("{organizationCode}")
    public ResponseEntity<OrganizationDto> getOrganizationByCode(@PathVariable  String organizationCode){
        return ResponseEntity.ok(organizationService.getOrganizationByCode(organizationCode));
    }
}
