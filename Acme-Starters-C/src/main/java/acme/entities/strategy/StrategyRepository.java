
package acme.entities.strategy;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StrategyRepository extends CrudRepository<Strategy, Integer> {

	@Query("select sum(t.expectedPercentage) from Tactic t where t.strategy.id = :strategyId")
	Double getExpectedPercentage(int strategyId);

	@Query("select s from Strategy s where s.ticker = :ticker")
	Strategy findStrategyByTicker(String ticker);

	@Query("select count(t) from Tactic t where t.strategy.id = :strategyId")
	Integer sumTacticsOfStrategy(int strategyId);
}
