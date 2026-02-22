
package acme.entities.audit;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AuditReportRepository extends CrudRepository<AuditReport, Integer> {

	@Query("SELECT SUM(s.hours) FROM AuditSection s WHERE s.report.id = ?1")
	Integer computeHours(Integer reportId);

	//Computa meses a decimal con funciones de MySQL: la diferencia en segundos entre la media de segundos por mes
	//@Query(value = "SELECT (UNIX_TIMESTAMP(end_moment) - UNIX_TIMESTAMP(start_moment)) / 2629746.0 FROM audit_report WHERE id = ?1", nativeQuery = true)
	//Double computeMonthsActive(Integer reportId); //TODO: HACERLO CON MOMENT HELPER

}
