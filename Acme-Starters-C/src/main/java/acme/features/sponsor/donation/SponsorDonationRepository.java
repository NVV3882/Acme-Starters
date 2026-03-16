
package acme.features.sponsor.donation;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface SponsorDonationRepository extends AbstractRepository {

	@Query("Select d from Donation d where d.sponsorship.id=:sponsorshipId")
	Collection<Donation> findDonationsBySponsorshipId(int sponsorshipId);

	@Query("Select s from Sponsorship s where s.id=:sponsorshipId")
	Sponsorship findSponsorshipBySponsorshipId(int sponsorshipId);

	@Query("Select d from Donation d where d.id=:donationId")
	Donation findDonationByDonationId(int donationId);
}
