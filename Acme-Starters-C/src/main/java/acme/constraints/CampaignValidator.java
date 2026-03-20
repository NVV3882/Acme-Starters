
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.campaign.Campaign;
import acme.entities.campaign.CampaignRepository;

@Validator
public class CampaignValidator extends AbstractValidator<ValidCampaign, Campaign> {

	@Autowired
	private CampaignRepository repositorio;


	@Override
	protected void initialise(final ValidCampaign annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Campaign campaign, final ConstraintValidatorContext context) {

		assert context != null;
		boolean result;
		if (campaign == null)
			result = true;
		else {
			{
				boolean campaignUnica;
				Campaign campaignExistente;

				campaignExistente = this.repositorio.findCampaignByTicker(campaign.getTicker());
				campaignUnica = campaignExistente == null || campaignExistente.equals(campaign);

				super.state(context, campaignUnica, "ticker", "acme.validation.campaign.duplicated-ticker.message");
			}
			{
				boolean publicadoConMilestone;
				boolean tieneMilestone = false;
				if (this.repositorio.getEffortById(campaign.getId()) != null)
					tieneMilestone = true;
				publicadoConMilestone = campaign.getDraftMode() || tieneMilestone;

				super.state(context, publicadoConMilestone, "*", "acme.validation.campaign.publicado-sin-milestone.message");

			}
			{
				if (campaign.getDraftMode().equals(false)) {
					boolean intervaloCorrectoTiempo;
					Date fechaInicio = campaign.getStartMoment();
					Date fechaFinal = campaign.getEndMoment();
					intervaloCorrectoTiempo = fechaInicio != null && fechaFinal != null && MomentHelper.isAfter(fechaFinal, fechaInicio);
					super.state(context, intervaloCorrectoTiempo, "startMoment", "acme.validation.campaign.intervalo-correcto-tiempo.message");
				}
			}
			result = !super.hasErrors(context);
		}

		return result;
	}

}
