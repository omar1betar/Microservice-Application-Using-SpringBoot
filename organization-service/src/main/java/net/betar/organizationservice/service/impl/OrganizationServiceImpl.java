package net.betar.organizationservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.betar.organizationservice.dto.OrganizationDto;
import net.betar.organizationservice.entity.Organization;
import net.betar.organizationservice.mapper.OrganizationMapper;
import net.betar.organizationservice.repository.OrganizationRepository;
import net.betar.organizationservice.service.OrganizationService;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;


    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {
        Organization organization = OrganizationMapper.mapToOrganization(organizationDto);
        Organization savedOrg = organizationRepository.save(organization);
        return OrganizationMapper.mapToOrganizationDto(savedOrg);
    }

    @Override
    public OrganizationDto getOrganizationByCode(String organizationCode) {
        Organization organization = organizationRepository.findByOrganizationCode(organizationCode);
        return OrganizationMapper.mapToOrganizationDto(organization);
    }
}
