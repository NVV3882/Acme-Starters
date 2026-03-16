
package acme.features.auditor.auditSection;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit.AuditReport;
import acme.entities.audit.AuditSection;

@Repository
public interface AuditorSectionRepository extends AbstractRepository {

	@Query("select s from AuditSection s where s.id = :id")
	AuditSection findAuditSectionById(int id);

	@Query("select r from AuditReport r where r.id = :reportId")
	AuditReport findAuditReportById(int reportId);

	@Query("select s from AuditSection s where s.report.id = :reportId")
	Collection<AuditSection> findSectionsByAuditReportId(int reportId);

}
