
package acme.features.any.milestone;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaign.Milestone;

@Repository
public interface AnyMilestoneRepository extends AbstractRepository {

	@Query("select m from Milestone m where m.campaign.id = :campaignId")
	Collection<Milestone> listAllMilestonesByCampaignId(int campaignId);

	@Query("select m from Milestone m where m.id = :id")
	Milestone showMilestoneById(int id);

	@Query("select count(c) > 0 from Campaign c where c.id = :campaignId and c.draftMode = false")
	Boolean campaignIsPublished(int campaignId);

	@Query("select count(m) > 0 from Milestone m where m.id = :id and m.campaign.draftMode = false")
	Boolean campaignIsPublishedByMilestoneId(int id);
}
