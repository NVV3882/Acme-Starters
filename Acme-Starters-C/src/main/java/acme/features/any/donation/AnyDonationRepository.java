
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
}
