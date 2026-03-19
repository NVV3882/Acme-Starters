
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("Select s from Sponsorship s where s.sponsor.id=:sponsorId")
	Collection<Sponsorship> listAllSponsorshipBySponsorId(int sponsorId);

	@Query("Select s from Sponsor s where s.id=:sponsorId")
	Sponsor findSponsorBySponsorId(int sponsorId);

	@Query("Select s from Sponsorship s where s.id=:sponsorshipId")
	Sponsorship showSponsorshipBySponsorshipId(int sponsorshipId);

	@Query("Select d from Donation d where d.sponsorship.id =:sponsorshipId")
	Collection<Donation> findDonationsBySponsorshipId(int sponsorshipId);
}
