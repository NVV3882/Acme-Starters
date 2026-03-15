
package acme.features.authenticated.inventor;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Inventor;

@Repository
public interface AuthenticatedInventorRepository extends AbstractRepository {

	@Query("select u from UserAccount u where u.id=:userAccountId")
	UserAccount findUserAccountById(int userAccountId);

	@Query("select i from Inventor i where i.userAccount.id =:userAccountId")
	Inventor findInventorByUserAccountId(int userAccountId);
}
