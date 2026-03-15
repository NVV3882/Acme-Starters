
package acme.features.any.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface AnySponsorshipRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.draftMode = false")
	Collection<Sponsorship> listAllSponsorships();

	@Query("select s from Sponsorship s where s.id=:id")
	Sponsorship showSponsorship(int id);

	@Query("select count(s) > 0 from Sponsorship s where s.id=:id and s.draftMode = false")
	Boolean sponsorshipIsPublished(int id);
}
