
package acme.features.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipCreateService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	SponsorSponsorshipRepository	repositorio;

	Sponsorship						patrocinio;


	@Override
	public void load() {
		int sponsorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		Sponsor patrocinador = this.repositorio.findSponsorBySponsorId(sponsorId);
		this.patrocinio = super.newObject(Sponsorship.class);
		this.patrocinio.setSponsor(patrocinador);
		this.patrocinio.setDraftMode(true);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
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
		this.repositorio.save(this.patrocinio);
	}
	@Override
	public void unbind() {
		super.unbindObject(this.patrocinio, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "totalMoney");
	}

}
