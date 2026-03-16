package acme.features.authenticated.fundraiser;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.strategy.Fundraiser;

@Repository
public interface AuthenticatedFundraiserRepository extends AbstractRepository {

	@Query("select u from UserAccount u where u.id=:userAccountId")
	UserAccount findUserAccountById(int userAccountId);

	@Query("select f from Fundraiser f where f.userAccount.id =:userAccountId")
	Fundraiser findFundraiserByUserAccountId(int userAccountId);
}
