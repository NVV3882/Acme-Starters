
package acme.entities.strategy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyRepository extends CrudRepository<Strategy, Integer> {

	@Query("SELECT SUM(t.expectedPercentaje) FROM Tactic t")
	Double getExpectedPercentaje();
}
