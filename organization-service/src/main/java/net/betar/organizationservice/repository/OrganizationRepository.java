package net.betar.organizationservice.repository;

import net.betar.organizationservice.dto.OrganizationDto;
import net.betar.organizationservice.entity.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization,Long> {
    Organization findByOrganizationCode(String organizationCode);
}
