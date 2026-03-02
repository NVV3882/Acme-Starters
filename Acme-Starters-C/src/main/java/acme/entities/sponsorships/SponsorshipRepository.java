
package acme.entities.sponsorships;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface SponsorshipRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.ticker = :ticker")
	Sponsorship findSponsorshipByTicker(String ticker);

	@Query("select sum(d.money.amount) from Donation d where d.sponsorship.id = :id ")
	Double sumDonationsOfSponsorship(int id);

	@Query("select count(d) from Donation d where d.sponsorship.id = :id and d.money.currency!='EUR'")
	int countNonEuroDonations(int id);

}
