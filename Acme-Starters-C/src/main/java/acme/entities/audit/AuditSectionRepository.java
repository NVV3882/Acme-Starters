
package acme.entities.audit;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AuditSectionRepository extends CrudRepository<AuditSection, Integer> {

	@Query("SELECT SUM(s.hours) FROM AuditSection s WHERE s.report.id = ?1")
	Integer computeHours(Integer reportId);


}

