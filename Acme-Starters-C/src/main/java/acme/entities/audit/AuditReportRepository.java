
package acme.entities.audit;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditReportRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.ticker = :ticker")
	AuditReport findAuditReportByTicker(String ticker);

	@Query("select d from Donation d where d.sponsorship.id = :id")
	List<AuditSection> findAuditSectionsByAuditReportId(int id);
}
