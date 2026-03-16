
package acme.features.any.campaign;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;

public class AnyCampaignShowService extends AbstractService<Any, Campaign> {

	@Autowired
	AnyCampaignRepository	repositorio;

	Campaign				campaña;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.campaña = this.repositorio.showCampaign(id);
	}

	@Override
	public void authorise() {
		int id;
		id = super.getRequest().getData("id", int.class);
		Boolean res;
		if (this.repositorio.campaignIsPublished(id).equals(true))
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaña, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "effort");

	}

}
