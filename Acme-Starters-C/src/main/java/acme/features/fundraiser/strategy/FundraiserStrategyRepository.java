package acme.features.fundraiser.strategy;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategy.Fundraiser;
import acme.entities.strategy.Strategy;
import acme.entities.strategy.Tactic;

@Repository
public interface FundraiserStrategyRepository extends AbstractRepository {

	@Query("select s from Strategy s where s.fundraiser.id = :fundraiserId")
	Collection<Strategy> listAllStrategiesByFundraiserId(int fundraiserId);

	@Query("select s from Strategy s where s.id = :id")
	Strategy findStrategyById(int id);

	@Query("select t from Tactic t where t.strategy.id = :id")
	Collection<Tactic> findTacticsByStrategyId(int id);

	@Query("select f from Fundraiser f where f.id = :fundraiserId")
	Fundraiser findFundraiserById(int fundraiserId);

}