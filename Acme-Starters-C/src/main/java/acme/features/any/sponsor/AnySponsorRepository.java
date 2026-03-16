
package acme.features.any.sponsor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.realms.Sponsor;

@Repository
public interface AnySponsorRepository extends AbstractRepository {

	@Query("Select s.sponsor from Sponsorship s where s.id=:sponsorshipId")
	Sponsor findSponsorBySponsorshipId(int sponsorshipId);

	@Query("Select count(s)>0 from Sponsorship s where s.id=:sponsorshipId and s.draftMode=false ")
	Boolean sponsorshipIsPublished(int sponsorshipId);
}
