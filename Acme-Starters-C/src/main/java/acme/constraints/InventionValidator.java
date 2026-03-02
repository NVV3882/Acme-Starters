
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.invention.Invention;
import acme.entities.invention.InventionRepository;

@Validator
public class InventionValidator extends AbstractValidator<ValidInvention, Invention> {

	//Estado interno

	@Autowired
	private InventionRepository repositorio;


	@Override
	protected void initialise(final ValidInvention annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Invention invention, final ConstraintValidatorContext context) {

		assert context != null;
		boolean result;
		if (invention == null)
			result = true;
		else {

			{
				boolean uniqueInvention;
				Invention existingInvention;

				existingInvention = this.repositorio.findInventionByTicker(invention.getTicker());
				uniqueInvention = existingInvention == null || existingInvention.equals(invention);

				super.state(context, uniqueInvention, "ticker", "acme.validation.Invention.duplicated-ticker.message");
			}
			{
				boolean correctInvention = false;
				Integer inventionId = invention.getId();

				Integer p = this.repositorio.findPartByInventionId(inventionId);

				if (p > 0)
					correctInvention = true;

				super.state(context, correctInvention, "*", "acme.validation.sponsorship.donacionesCorrectas.message");
			}

			result = !super.hasErrors(context);
		}
		return result;
	}

}
