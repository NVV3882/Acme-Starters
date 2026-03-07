
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.sponsorships.SponsorshipRepository;

@Validator
public class SponsorshipValidator extends AbstractValidator<ValidSponsorship, Sponsorship> {

	//Estado interno

	@Autowired
	private SponsorshipRepository repositorio;


	@Override
	protected void initialise(final ValidSponsorship annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Sponsorship patrocinio, final ConstraintValidatorContext context) {

		assert context != null;
		boolean result;
		if (patrocinio == null)
			result = true;
		else {
			{
				boolean patrocinioUnico;
				Sponsorship patrocinioExistente;

				patrocinioExistente = this.repositorio.findSponsorshipByTicker(patrocinio.getTicker());
				patrocinioUnico = patrocinioExistente == null || patrocinioExistente.equals(patrocinio);

				super.state(context, patrocinioUnico, "ticker", "acme.validation.sponsorship.duplicated-ticker.message");
			}
			{
				boolean publicadoConDonaciones;
				boolean tieneDonaciones = false;
				if (this.repositorio.sumDonationsOfSponsorship(patrocinio.getId()) != null)
					tieneDonaciones = true;
				publicadoConDonaciones = patrocinio.getDraftMode() || tieneDonaciones;

				super.state(context, publicadoConDonaciones, "*", "acme.validation.sponsorship.publicado-sin-donaciones.message");
			}
			{
				boolean intervaloCorrectoTiempo;
				Date fechaInicio = patrocinio.getStartMoment();
				Date fechaFinal = patrocinio.getEndMoment();
				if (patrocinio.getDraftMode().equals(false))
					intervaloCorrectoTiempo = fechaFinal.after(fechaInicio);
				else
					intervaloCorrectoTiempo = true;
				super.state(context, intervaloCorrectoTiempo, "*", "acme.validation.sponsorship.intervalo-correcto-tiempo.message");
			}

			{
				boolean sonDonacionesEnEuros;

				sonDonacionesEnEuros = this.repositorio.countNonEuroDonations(patrocinio.getId()) == 0;

				super.state(context, sonDonacionesEnEuros, "*", "acme.validation.sponsorship.son-donaciones-en-euros.message");
			}
			result = !super.hasErrors(context);
		}
		return result;
	}

}
