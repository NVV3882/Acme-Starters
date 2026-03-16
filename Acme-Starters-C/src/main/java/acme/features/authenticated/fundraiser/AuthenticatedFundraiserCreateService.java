package acme.features.authenticated.fundraiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.components.principals.UserAccount;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.entities.strategy.Fundraiser;

@Service
public class AuthenticatedFundraiserCreateService extends AbstractService<Authenticated, Fundraiser> {

	@Autowired
	AuthenticatedFundraiserRepository	repositorio;

	Fundraiser						recaudador;


	@Override
	public void load() {
		int userAccountId = super.getRequest().getPrincipal().getAccountId();
		UserAccount userAccount = this.repositorio.findUserAccountById(userAccountId);

		this.recaudador = super.newObject(Fundraiser.class);
		this.recaudador.setUserAccount(userAccount);
	}

	@Override
	public void authorise() {
		boolean status = !this.getRequest().getPrincipal().hasRealmOfType(Fundraiser.class);
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.recaudador, "bank", "statement", "agent");
	}

	@Override
	public void validate() {
		super.validateObject(this.recaudador);
	}

	@Override
	public void execute() {
		this.repositorio.save(this.recaudador);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.recaudador, "bank", "statement", "agent");
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
