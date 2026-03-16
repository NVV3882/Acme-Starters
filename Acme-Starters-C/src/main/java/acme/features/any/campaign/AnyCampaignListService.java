
package acme.features.any.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaign.Campaign;

@Service
public class AnyCampaignListService extends AbstractService<Any, Campaign> {

	@Autowired
	AnyCampaignRepository	repositorio;

	Collection<Campaign>	campañas;


	@Override
	public void load() {
		this.campañas = this.repositorio.listAllCampaigns();
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.campañas, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "effort");
	}

}
