
package acme.features.any.auditor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.realms.Auditor;

@Repository
public interface AnyAuditorRepository extends AbstractRepository {

	@Query("Select r.auditor from AuditReport r where r.id=:reportId")
	Auditor findAuditorByReportId(int reportId);

	@Query("Select count(r)>0 from AuditReport r where r.id=:reportId and r.draftMode=false ")
	Boolean reportIsPublished(int reportId);
}
