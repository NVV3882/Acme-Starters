package acme.features.any.tactic;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategy.Tactic;

@Repository
public interface AnyTacticRepository extends AbstractRepository {

	@Query("select t from Tactic t where t.strategy.id = :strategyId")
	Collection<Tactic> listAllTacticsByStrategyId(int strategyId);

	@Query("select t from Tactic t where t.id = :id")
	Tactic showTacticById(int id);

	@Query("select count(s) > 0 from Strategy s where s.id = :strategyId and s.draftMode = false")
	Boolean strategyIsPublished(int strategyId);

	@Query("select count(t) > 0 from Tactic t where t.id = :id and t.strategy.draftMode = false")
	Boolean strategyIsPublishedByTacticId(int id);
}
