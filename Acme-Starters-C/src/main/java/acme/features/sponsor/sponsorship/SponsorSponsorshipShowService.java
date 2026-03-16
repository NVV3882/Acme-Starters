
package acme.features.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipShowService extends AbstractService<Sponsor, Sponsorship> {

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
		int sponsorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		boolean res;
		if (this.patrocinio.getSponsor().getId() == sponsorId)
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
