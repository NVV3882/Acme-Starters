
package acme.features.any.auditSection;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit.AuditSection;

@Repository
public interface AnySectionRepository extends AbstractRepository {

	@Query("Select s from AuditSection s where s.report.id= :reportId")
	Collection<AuditSection> listAllSectionsByReportId(int reportId);

	@Query("Select s from AuditSection s where s.id = :id")
	AuditSection showSectionById(int id);

}
