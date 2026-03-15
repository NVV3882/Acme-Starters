
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
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
				Boolean uniqueInvention;
				Invention existingInvention;

				existingInvention = this.repositorio.findInventionByTicker(invention.getTicker());
				uniqueInvention = existingInvention == null || existingInvention.equals(invention);

				super.state(context, uniqueInvention, "ticker", "acme.validation.invention.duplicated-ticker.message");
			}
			{
				boolean correctParts = true;

				Integer numberOfParts = this.repositorio.findNumberOfPartsOfAInventionByInventionId(invention.getId());

				if (invention.getDraftMode() == Boolean.FALSE && numberOfParts == 0) // compruebo que si se publica un invento, este tiene que tener partes asociadadas.
					correctParts = false;

				super.state(context, correctParts, "*", "acme.validation.invention.published-without-parts.message");
			}
			{
				boolean correctDates = true;

				//				if (MomentHelper.getBaseMoment().before(invention.getStartMoment()) && MomentHelper.getBaseMoment().before(invention.getEndMoment()))
				//					correctDates = true;

				if (invention.getDraftMode().equals(false))
					if (MomentHelper.isAfter(invention.getStartMoment(), invention.getEndMoment()))
						correctDates = false;

				super.state(context, correctDates, "*", "acme.validation.invention.incorrect-dates-intervale.message");
			}
			{
				boolean correctCost = false;

				Integer numeroPartes = this.repositorio.countPartOfAnInventionWithCurrencyNotInEurosByInventionId(invention.getId());

				if (numeroPartes == 0) // compruebo que las partes asociadas a un invento estén siempre en euros. Si un invento todavia no tiene partes asociadas pues no hay problema. 
					correctCost = true;

				// compruebo que estén en euros siempre, no solo cuando se publica el invento (draftMode = false)

				super.state(context, correctCost, "*", "acme.validation.invention.currency.message");
			}

			result = !super.hasErrors(context);
		}
		return result;
	}

}
