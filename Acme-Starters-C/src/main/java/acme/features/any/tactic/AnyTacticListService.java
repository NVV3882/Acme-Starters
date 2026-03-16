package acme.features.any.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategy.Tactic;

@Service
public class AnyTacticListService extends AbstractService<Any, Tactic> {

	@Autowired
	AnyTacticRepository	repositorio;

	Collection<Tactic>		tactics;


	@Override
	public void load() {
		int strategyId;
		strategyId = super.getRequest().getData("strategyId", int.class);
		this.tactics = this.repositorio.listAllTacticsByStrategyId(strategyId);
	}

	@Override
	public void authorise() {
		Boolean res;
		int strategyId;
		strategyId = super.getRequest().getData("strategyId", int.class);
		if (this.repositorio.strategyIsPublished(strategyId).equals(true))
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.tactics, "name", "expectedPercentage", "kind");
	}
}
