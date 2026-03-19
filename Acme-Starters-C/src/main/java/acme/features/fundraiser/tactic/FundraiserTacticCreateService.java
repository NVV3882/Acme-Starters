package acme.features.fundraiser.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.strategy.Fundraiser;
import acme.entities.strategy.Strategy;
import acme.entities.strategy.Tactic;
import acme.entities.strategy.TacticKind;

@Service
public class FundraiserTacticCreateService extends AbstractService<Fundraiser, Tactic> {

	@Autowired
	private FundraiserTacticRepository	repository;

	private Tactic						tactic;


	@Override
	public void load() {
		int strategyId = super.getRequest().getData("strategyId", int.class);
		Strategy strategy = this.repository.findStrategyById(strategyId);

		this.tactic = super.newObject(Tactic.class);
		this.tactic.setStrategy(strategy);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.tactic != null && this.tactic.getStrategy() != null && this.tactic.getStrategy().getDraftMode() && this.tactic.getStrategy().getFundraiser().isPrincipal());
	}

	@Override
	public void bind() {
		super.bindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.tactic);

		Double currentPercentage = this.tactic.getStrategy().getExpectedPercentage();
		Double tacticPercentage = this.tactic.getExpectedPercentage();
		if (currentPercentage != null && tacticPercentage != null) {
			boolean percentageValid = currentPercentage + tacticPercentage <= 100.00;
			super.state(percentageValid, "expectedPercentage", "fundraiser.tactic.valid.score");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.tactic);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(TacticKind.class, this.tactic.getKind());

		tuple = super.unbindObject(this.tactic, "name", "notes", "expectedPercentage", "kind");
		tuple.put("strategyId", this.tactic.getStrategy().getId());
		tuple.put("draftMode", this.tactic.getStrategy().getDraftMode());
		tuple.put("kinds", choices);
	}

}