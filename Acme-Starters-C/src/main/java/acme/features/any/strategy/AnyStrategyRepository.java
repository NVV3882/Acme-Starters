
package acme.features.any.strategy;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategy.Strategy;

@Repository
public interface AnyStrategyRepository extends AbstractRepository {

	@Query("select s from Strategy s where s.draftMode = false")
	Collection<Strategy> listAllStrategies();

	@Query("select s from Strategy s where s.id=:id")
	Strategy showStrategy(int id);

	@Query("select count(s) > 0 from Strategy s where s.id=:id and s.draftMode = false")
	Boolean strategyIsPublished(int id);

}
