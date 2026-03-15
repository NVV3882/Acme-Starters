
package acme.features.any.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;

@Service
public class AnySponsorshipShowService extends AbstractService<Any, Sponsorship> {

	@Autowired
	AnySponsorshipRepository	repositorio;

	Sponsorship					patrocinio;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.patrocinio = this.repositorio.showSponsorship(id);
	}
	@Override
	public void authorise() {
		int id;
		id = super.getRequest().getData("id", int.class);
		Boolean res;
		if (this.repositorio.sponsorshipIsPublished(id).equals(true))
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.patrocinio, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "totalMoney");

	}

}
