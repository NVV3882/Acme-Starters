
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
	public boolean isValid(final Strategy value, final ConstraintValidatorContext context) {
		assert context != null;
		boolean result;
		if (value == null)
			result = true;
		else {
			{
				boolean strategyUnico;
				Strategy strategyExistente;

				strategyExistente = this.repository.findStrategyByTicker(value.getTicker());
				strategyUnico = strategyExistente == null || strategyExistente.equals(value);

				super.state(context, strategyUnico, "ticker", "acme.validation.strategy.duplicated-ticker.message");
			}
			{
				boolean tactics;
				boolean tieneTactics = false;
				if (this.repository.sumTacticsOfStrategy(value.getId()) != null)
					tieneTactics = true;
				tactics = value.getDraftMode() || tieneTactics;

				super.state(context, tactics, "*", "acme.validation.strategy.publicado-sin-tactics.message");
			}
			{
				boolean intervaloCorrectoTiempo;
				Date fechaInicio = value.getStartMoment();
				Date fechaFinal = value.getEndMoment();
				if (value.getDraftMode().equals(false))
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
