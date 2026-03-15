
package acme.entities.audit;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface AuditReportRepository extends AbstractRepository {

	@Query("select a from AuditReport a where a.ticker = :ticker")
	AuditReport findAuditReportByTicker(String ticker);

	@Query("select s from AuditSection s where s.report.id = :id")
	List<AuditSection> findAuditSectionsByAuditReportId(int id);

	@Query("select s from AuditSection s where s.report.ticker = :ticker")
	List<AuditSection> findAuditSectionsByAuditReportTicker(String ticker);

	@Query("SELECT SUM(s.hours) FROM AuditSection s WHERE s.report.id = ?1")
	Integer computeHours(Integer reportId);
}
