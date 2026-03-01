
package acme.constraints;

import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
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
				intervaloCorrectoTiempo = fechaFinal.after(fechaInicio) && MomentHelper.getBaseMoment().before(fechaInicio);
				super.state(context, intervaloCorrectoTiempo, "*", "acme.validation.sponsorship.intervalo-correcto-tiempo.message");
			}
			{
				boolean sumaDonacionesCorrectas;

				Double sumaReal = this.repositorio.sumDonationsOfSponsorship(patrocinio.getId());
				if (sumaReal == null)
					sumaReal = 0.0;
				Double sumaMemoria = patrocinio.totalMoney().getAmount();
				sumaDonacionesCorrectas = sumaReal.equals(sumaMemoria);

				super.state(context, sumaDonacionesCorrectas, "*", "acme.validation.sponsorship.suma-donaciones-correctas.message");
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
