
package acme.features.sponsor.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.DonationKind;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorDonationCreateService extends AbstractService<Sponsor, Donation> {

	@Autowired
	SponsorDonationRepository	repository;

	Donation					donation;


	@Override
	public void load() {
		int sponsorshipId;
		Sponsorship sponsorship;

		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		sponsorship = this.repository.findSponsorshipBySponsorshipId(sponsorshipId);

		this.donation = super.newObject(Donation.class);
		this.donation.setSponsorship(sponsorship);
	}

	@Override
	public void authorise() {
		boolean res;
		int sponsorshipId;
		Sponsorship patrocinio;
		sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		patrocinio = this.repository.findSponsorshipBySponsorshipId(sponsorshipId);
		res = patrocinio != null && patrocinio.getDraftMode().equals(true) && patrocinio.getSponsor().isPrincipal();

		super.setAuthorised(res);
	}

	@Override
	public void bind() {
		super.bindObject(this.donation, "name", "notes", "money", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.donation);
	}

	@Override
	public void execute() {
		this.repository.save(this.donation);
	}

	@Override
	public void unbind() {

		SelectChoices choices = SelectChoices.from(DonationKind.class, this.donation.getKind());

		Tuple tupla = super.unbindObject(this.donation, "name", "notes", "money", "kind");
		tupla.put("sponsorshipId", super.getRequest().getData("sponsorshipId", int.class));
		tupla.put("draftMode", this.donation.getSponsorship().getDraftMode());
		tupla.put("kinds", choices);

	}
	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
