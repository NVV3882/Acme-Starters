
package acme.constraints;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.strategy.Strategy;
import acme.entities.strategy.StrategyRepository;

@Validator
public class StrategyValidator extends AbstractValidator<ValidStrategy, Strategy> {

	@Autowired
	private StrategyRepository repository;


	@Override
	protected void initialise(final ValidStrategy annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Strategy strategy, final ConstraintValidatorContext context) {
		assert context != null;
		boolean result;
		if (strategy == null)
			result = true;
		else {
			{
				boolean strategyUnico;
				Strategy strategyExistente;

				strategyExistente = this.repository.findStrategyByTicker(strategy.getTicker());
				strategyUnico = strategyExistente == null || strategyExistente.equals(strategy);

				super.state(context, strategyUnico, "ticker", "acme.validation.strategy.duplicated-ticker.message");
			}
			{
				boolean tactics;
				boolean tieneTactics = false;
				if (this.repository.sumTacticsOfStrategy(strategy.getId()) != null)
					tieneTactics = true;
				tactics = Boolean.TRUE.equals(strategy.getDraftMode()) || tieneTactics;

				super.state(context, tactics, "*", "acme.validation.strategy.publicado-sin-tactics.message");
			}
			{
				boolean intervaloCorrectoTiempo;
				Date fechaInicio = strategy.getStartMoment();
				Date fechaFinal = strategy.getEndMoment();
				if (Boolean.FALSE.equals(strategy.getDraftMode()))
					intervaloCorrectoTiempo = MomentHelper.computeDifference(fechaInicio, fechaFinal, ChronoUnit.DAYS) >= 1;
				else
					intervaloCorrectoTiempo = true;
				super.state(context, intervaloCorrectoTiempo, "*", "acme.validation.strategy.intervalo-correcto-tiempo.message");
			}

			result = !super.hasErrors(context);
		}
		return result;
	}

}
