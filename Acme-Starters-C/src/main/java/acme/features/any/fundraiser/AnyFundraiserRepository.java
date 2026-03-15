package acme.features.any.fundraiser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategy.Fundraiser;

@Repository
public interface AnyFundraiserRepository extends AbstractRepository {

	@Query("select s.fundraiser from Strategy s where s.id = :strategyId")
	Fundraiser findFundraiserByStrategyId(int strategyId);

	@Query("select count(s) > 0 from Strategy s where s.id = :strategyId and s.draftMode = false")
	Boolean strategyIsPublished(int strategyId);
}
