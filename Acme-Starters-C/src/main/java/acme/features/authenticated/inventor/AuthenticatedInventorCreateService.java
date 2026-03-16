
package acme.features.authenticated.inventor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Inventor;

@Service
public class AuthenticatedInventorCreateService extends AbstractService<Authenticated, Inventor> {

	@Autowired
	AuthenticatedInventorRepository	repositorio;

	Inventor						inventor;


	@Override
	public void load() {
		int userAccountId = super.getRequest().getPrincipal().getAccountId();
		UserAccount userAccount = this.repositorio.findUserAccountById(userAccountId);

		this.inventor = super.newObject(Inventor.class);
		this.inventor.setUserAccount(userAccount);
	}

	@Override
	public void authorise() {
		boolean status = !this.getRequest().getPrincipal().hasRealmOfType(Inventor.class);
		super.setAuthorised(status);
	}
	@Override
	public void bind() {
		super.bindObject(this.inventor, "bio", "keyWords", "licensed");
	}
	@Override
	public void validate() {
		super.validateObject(this.inventor);
	}
	@Override
	public void execute() {
		this.repositorio.save(this.inventor);
	}
	@Override
	public void unbind() {
		super.unbindObject(this.inventor, "bio", "keyWords", "licensed");
	}
	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
