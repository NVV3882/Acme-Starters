
package acme.constraints;

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
				boolean publicadoConDonaciones = true;

				publicadoConDonaciones = patrocinio.getDraftMode() || this.repositorio.sumDonationsOfSponsorship(patrocinio.getId()) == null;

				super.state(context, publicadoConDonaciones, "*", "acme.validation.sponsorship.publicadoConDonaciones.message");
			}
			{
				boolean patrocinioUnico;
				Sponsorship patrocinioExistente;

				patrocinioExistente = this.repositorio.findSponsorshipByTicker(patrocinio.getTicker());
				patrocinioUnico = patrocinioExistente == null || patrocinioExistente.equals(patrocinio);

				super.state(context, patrocinioUnico, "ticker", "acme.validation.sponsorship.duplicated-ticker.message");
			}
			{
				boolean sumaDonacionesCorrectas;

				Double sumaReal = this.repositorio.sumDonationsOfSponsorship(patrocinio.getId());
				Double sumaMemoria = patrocinio.totalMoney().getAmount();
				sumaDonacionesCorrectas = sumaReal.equals(sumaMemoria);

				super.state(context, sumaDonacionesCorrectas, "*", "acme.validation.sponsorship.sumaDonacionesCorrectas.message");
			}
			{
				boolean sonDonacionesEnEuros;

				sonDonacionesEnEuros = this.repositorio.countNonEuroDonations(patrocinio.getId()) == 0;

				super.state(context, sonDonacionesEnEuros, "*", "acme.validation.sponsorship.sonDonacionesEnEuros.message");
			}
			result = !super.hasErrors(context);
		}
		return result;
	}

}
