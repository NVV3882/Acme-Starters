
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
public class SponsorDonationDeleteService extends AbstractService<Sponsor, Donation> {

	@Autowired
	SponsorDonationRepository	repositorio;

	Donation					donacion;


	@Override
	public void load() {
		int donacionId = super.getRequest().getData("id", int.class);
		this.donacion = this.repositorio.findDonationByDonationId(donacionId);
	}
	@Override
	public void authorise() {
		Boolean res;
		int sponsorshipId = this.donacion.getSponsorship().getId();
		Sponsorship patrocinio = this.repositorio.findSponsorshipBySponsorshipId(sponsorshipId);
		if (patrocinio.getDraftMode().equals(true) && patrocinio.getSponsor().isPrincipal())
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}
	@Override
	public void bind() {
		super.bindObject(this.donacion, "name", "notes", "money", "kind");
	}
	@Override
	public void validate() {

	}
	@Override
	public void execute() {
		this.repositorio.delete(this.donacion);
	}
	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(DonationKind.class, this.donacion.getKind());

		tuple = super.unbindObject(this.donacion, "name", "notes", "money", "kind");
		tuple.put("sponsorshipId", this.donacion.getSponsorship().getId());
		tuple.put("draftMode", this.donacion.getSponsorship().getDraftMode());
		tuple.put("kinds", choices);
	}
}
