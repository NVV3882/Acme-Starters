
package acme.entities.sponsorships;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorshipRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.ticker = :ticker")
	Sponsorship findSponsorshipByTicker(String ticker);

	@Query("select d from Donation d where d.sponsorship.id = :id")
	List<Donation> findDonationsBySponsorshipId(int id);
}
