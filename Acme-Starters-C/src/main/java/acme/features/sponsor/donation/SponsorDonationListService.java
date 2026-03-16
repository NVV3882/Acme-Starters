
package acme.features.sponsor.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorDonationListService extends AbstractService<Sponsor, Donation> {

	@Autowired
	SponsorDonationRepository	repositorio;

	Collection<Donation>		donaciones;


	@Override
	public void load() {
		int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		this.donaciones = this.repositorio.findDonationsBySponsorshipId(sponsorshipId);
	}
	@Override
	public void authorise() {
		Boolean res;
		int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		Sponsorship patrocinio = this.repositorio.findSponsorshipBySponsorshipId(sponsorshipId);
		if (patrocinio.getSponsor().isPrincipal())
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}
	@Override
	public void unbind() {
		int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		super.unbindObjects(this.donaciones, "name", "notes", "money", "kind");
		super.unbindGlobal("sponsorshipId", sponsorshipId);

	}
}
