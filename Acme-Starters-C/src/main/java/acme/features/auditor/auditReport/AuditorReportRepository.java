
package acme.features.auditor.auditReport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit.AuditReport;
import acme.entities.audit.AuditSection;

@Repository
public interface AuditorReportRepository extends AbstractRepository {

	@Query("select ar from AuditReport ar where ar.auditor.id = :auditorId")
	Collection<AuditReport> findAuditReportsByAuditorId(int auditorId);

	@Query("select ar from AuditReport ar where ar.id = :id")
	AuditReport findAuditReportById(int id);

	@Query("select s from AuditSection s where s.report.id = :reportId")
	Collection<AuditSection> findSectionsByAuditReportId(int reportId);

}
