
package acme.features.any.donation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;

@Service
public class AnyDonationShowService extends AbstractService<Any, Donation> {

	@Autowired
	AnyDonationRepository	repositorio;

	Donation				donacion;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.donacion = this.repositorio.showDonationById(id);
	}
	@Override
	public void authorise() {
		Boolean res;
		int id = super.getRequest().getData("id", int.class);
		if (this.repositorio.sponsorshipIsPublishedByDonationId(id).equals(true))
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}
	@Override
	public void unbind() {
		super.unbindObject(this.donacion, "name", "notes", "money", "kind");
	}

}
