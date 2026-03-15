
package acme.features.authenticated.sponsor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Sponsor;

@Repository
public interface AuthenticatedSponsorRepository extends AbstractRepository {

	@Query("select u from UserAccount u where u.id=:userAccountId")
	UserAccount findUserAccountById(int userAccountId);

	@Query("select s from Sponsor s where s.userAccount.id =:userAccountId")
	Sponsor findSponsorByUserAccountId(int userAccountId);
}
