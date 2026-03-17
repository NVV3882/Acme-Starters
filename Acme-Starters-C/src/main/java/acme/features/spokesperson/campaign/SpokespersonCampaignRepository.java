
package acme.features.spokesperson.campaign;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaign.Campaign;
import acme.entities.campaign.Milestone;

@Repository
public interface SpokespersonCampaignRepository extends AbstractRepository {

	@Query("select c from Campaign c where c.spokesperson.id = :spokespersonId")
	Collection<Campaign> listAllCampaignsBySpokespersonId(int spokespersonId);

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);

	@Query("select m from Milestone m where m.campaign.id = :id")
	Collection<Milestone> findMilestonesByCampaignId(int id);
}
