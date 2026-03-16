
package acme.features.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.DonationKind;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorDonationShowService extends AbstractService<Sponsor, Donation> {

	@Autowired
	SponsorDonationRepository	repositorio;

	Donation					donacion;


	@Override
	public void load() {
		int donationId = super.getRequest().getData("id", int.class);
		this.donacion = this.repositorio.findDonationByDonationId(donationId);
	}
	@Override
	public void authorise() {
		Boolean res;
		int sponsorshipId = this.donacion.getSponsorship().getId();
		Sponsorship patrocinio = this.repositorio.findSponsorshipBySponsorshipId(sponsorshipId);
		if (patrocinio.getSponsor().isPrincipal())
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}
	@Override
	public void unbind() {
		Tuple tupla;
		SelectChoices tipos = SelectChoices.from(DonationKind.class, this.donacion.getKind());
		tupla = super.unbindObject(this.donacion, "name", "notes", "money", "kind");
		tupla.put("sponsorshipId", this.donacion.getSponsorship().getId());
		tupla.put("kinds", tipos);
		tupla.put("draftMode", this.donacion.getSponsorship().getDraftMode());

	}
}
