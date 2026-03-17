
package acme.features.authenticated.spokesperson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Spokesperson;

@Service
public class AuthenticatedSpokespersonCreateService extends AbstractService<Authenticated, Spokesperson> {

	@Autowired
	AuthenticatedSpokespersonRepository	repositorio;

	Spokesperson						spokesperson;


	@Override
	public void load() {
		int userAccountId = super.getRequest().getPrincipal().getAccountId();
		UserAccount userAccount = this.repositorio.findUserAccountById(userAccountId);

		this.spokesperson = super.newObject(Spokesperson.class);
		this.spokesperson.setUserAccount(userAccount);
	}

	@Override
	public void authorise() {
		boolean status = !this.getRequest().getPrincipal().hasRealmOfType(Spokesperson.class);
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.spokesperson, "cv", "achievements", "licensed");
	}

	@Override
	public void validate() {
		super.validateObject(this.spokesperson);
	}

	@Override
	public void execute() {
		this.repositorio.save(this.spokesperson);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.spokesperson, "cv", "achievements", "licensed");
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
