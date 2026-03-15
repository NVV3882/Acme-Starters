
package acme.features.any.sponsor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.Sponsor;

@Service
public class AnySponsorShowService extends AbstractService<Any, Sponsor> {

	@Autowired
	AnySponsorRepository	repositorio;

	Sponsor					patrocinador;


	@Override
	public void load() {
		int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		this.patrocinador = this.repositorio.findSponsorBySponsorshipId(sponsorshipId);
	}
	@Override
	public void authorise() {
		int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		Boolean res;
		if (this.repositorio.sponsorshipIsPublished(sponsorshipId).equals(true))
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.patrocinador, "address", "im", "gold");
	}
}
