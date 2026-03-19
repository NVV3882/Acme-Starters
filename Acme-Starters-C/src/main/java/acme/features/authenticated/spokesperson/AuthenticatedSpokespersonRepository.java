
package acme.features.authenticated.spokesperson;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.realms.Spokesperson;

@Repository
public interface AuthenticatedSpokespersonRepository extends AbstractRepository {

	@Query("select u from UserAccount u where u.id=:userAccountId")
	UserAccount findUserAccountById(int userAccountId);

	@Query("select s from Spokesperson s where s.userAccount.id =:userAccountId")
	Spokesperson findSpokespersonByUserAccountId(int userAccountId);
}
