
package acme.features.any.milestone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaign.Milestone;

@Service
public class AnyMilestoneShowService extends AbstractService<Any, Milestone> {

	@Autowired
	AnyMilestoneRepository	repositorio;

	Milestone				milestone;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.milestone = this.repositorio.showMilestoneById(id);
	}

	@Override
	public void authorise() {
		Boolean res;
		int id = super.getRequest().getData("id", int.class);

		if (this.repositorio.campaignIsPublishedByMilestoneId(id).equals(true))
			res = true;
		else
			res = false;

		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
	}
}
