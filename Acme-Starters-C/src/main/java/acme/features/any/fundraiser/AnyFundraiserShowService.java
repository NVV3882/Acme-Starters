package acme.features.any.fundraiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategy.Fundraiser;

@Service
public class AnyFundraiserShowService extends AbstractService<Any, Fundraiser> {

	@Autowired
	AnyFundraiserRepository	repositorio;

	Fundraiser				recaudador;


	@Override
	public void load() {
		int strategyId = super.getRequest().getData("strategyId", int.class);
		this.recaudador = this.repositorio.findFundraiserByStrategyId(strategyId);
	}

	@Override
	public void authorise() {
		int strategyId = super.getRequest().getData("strategyId", int.class);
		Boolean res;
		if (this.repositorio.strategyIsPublished(strategyId).equals(true))
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.recaudador, "bank", "statement", "agent");
	}
}
