
package acme.features.any.donation;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;

@Service
public class AnyDonationListService extends AbstractService<Any, Donation> {

	@Autowired
	AnyDonationRepository	repositorio;

	Collection<Donation>	donaciones;


	@Override
	public void load() {
		int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		this.donaciones = this.repositorio.listAllDonationsBySponsorshipId(sponsorshipId);
	}
	@Override
	public void authorise() {
		Boolean res;
		int sponsorshipId = super.getRequest().getData("sponsorshipId", int.class);
		if (this.repositorio.sponsorshipIsPublished(sponsorshipId).equals(true))
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.donaciones, "name", "notes", "money", "kind");
	}
}
