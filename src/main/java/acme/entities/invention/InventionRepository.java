
package acme.entities.invention;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface InventionRepository extends AbstractRepository {

	@Query("select s from Invention s where s.ticker = :ticker")
	Invention findInventionByTicker(String ticker);

	@Query("select count(p) from Part p where p.invention.id = :id")
	Integer findPartByInventionId(int id);

	@Query("select count(p.cost.amount) from Part p where p.invention.id = :id ")
	Double sumCostOfThePartsOfAInvention(int id);

}
