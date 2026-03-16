
package acme.features.any.auditReport;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.audit.AuditReport;

@Repository
public interface AnyReportRepository extends AbstractRepository {

	@Query("select a from AuditReport a where a.draftMode = false")
	Collection<AuditReport> listAllAuditReports();

	@Query("select a from AuditReport a where a.id=:id")
	AuditReport showAuditReport(int id);

	@Query("select count(r) > 0 from AuditReport r where r.id=:id and r.draftMode = false")
	Boolean reportIsPublished(int id);
}
