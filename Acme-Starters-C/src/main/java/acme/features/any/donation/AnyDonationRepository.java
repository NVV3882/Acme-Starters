
package acme.features.any.donation;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Donation;

@Repository
public interface AnyDonationRepository extends AbstractRepository {

	@Query("Select d from Donation d where d.sponsorship.id= :sponsorshipId")
	Collection<Donation> listAllDonationsBySponsorshipId(int sponsorshipId);

	@Query("Select d from Donation d where d.id = :id")
	Donation showDonationById(int id);

	@Query("select count(s) > 0 from Sponsorship s where s.id=:sponsorshipId and s.draftMode = false")
	Boolean sponsorshipIsPublished(int sponsorshipId);

	@Query("select count(d)>0 from Donation d where d.id = :id and d.sponsorship.draftMode= false")
	Boolean sponsorshipIsPublishedByDonationId(int id);
}
