
package acme.features.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipDeleteService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	SponsorSponsorshipRepository	repositorio;

	Sponsorship						patrocinio;


	@Override
	public void load() {
		int sponsorshipId = super.getRequest().getData("id", int.class);
		this.patrocinio = this.repositorio.showSponsorshipBySponsorshipId(sponsorshipId);
	}
	@Override
	public void authorise() {
		boolean res;
		if (this.patrocinio.getDraftMode().equals(true) && this.patrocinio.getSponsor().isPrincipal())
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}
	@Override
	public void bind() {
		super.bindObject(this.patrocinio, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}
	@Override
	public void validate() {
		super.validateObject(this.patrocinio);
	}
	@Override
	public void execute() {
		this.repositorio.delete(this.patrocinio);
	}
	@Override
	public void unbind() {
		super.unbindObject(this.patrocinio, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "totalMoney");
	}
}
