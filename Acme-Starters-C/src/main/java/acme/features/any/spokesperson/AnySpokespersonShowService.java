
package acme.features.any.spokesperson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.realms.Spokesperson;

@Service
public class AnySpokespersonShowService extends AbstractService<Any, Spokesperson> {

	@Autowired
	AnySpokespersonRepository	repositorio;

	Spokesperson				spokesperson;


	@Override
	public void load() {
		int campaignId = super.getRequest().getData("campaignId", int.class);
		this.spokesperson = this.repositorio.findSpokespersonByCampaignId(campaignId);
	}

	@Override
	public void authorise() {

		Boolean res;
		int campaignId = super.getRequest().getData("campaignId", int.class);

		if (this.repositorio.campaignIsPublished(campaignId).equals(true))
			res = true;
		else
			res = false;

		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.spokesperson, "cv", "achievements", "licensed");
	}
}
