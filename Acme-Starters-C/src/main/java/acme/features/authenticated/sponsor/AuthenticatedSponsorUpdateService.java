
package acme.features.authenticated.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Authenticated;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.realms.Sponsor;

@Service
public class AuthenticatedSponsorUpdateService extends AbstractService<Authenticated, Sponsor> {

	@Autowired
	AuthenticatedSponsorRepository	repositorio;

	Sponsor							patrocinador;


	@Override
	public void load() {
		int userAccountId = super.getRequest().getPrincipal().getAccountId();
		this.patrocinador = this.repositorio.findSponsorByUserAccountId(userAccountId);

	}
	@Override
	public void authorise() {
		boolean status = this.getRequest().getPrincipal().hasRealmOfType(Sponsor.class);
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.patrocinador, "address", "im", "gold");
	}
	@Override
	public void validate() {
		super.validateObject(this.patrocinador);
	}
	@Override
	public void execute() {
		this.repositorio.save(this.patrocinador);
	}
	@Override
	public void unbind() {
		super.unbindObject(this.patrocinador, "address", "im", "gold");
	}
	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
