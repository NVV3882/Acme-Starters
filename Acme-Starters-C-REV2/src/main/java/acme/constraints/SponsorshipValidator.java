
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.sponsorships.Donation;
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
				boolean donacionesCorrectas;
				java.util.List<Donation> donations;

				donations = this.repositorio.findDonationsBySponsorshipId(patrocinio.getId());
				donacionesCorrectas = donations != null && !donations.isEmpty();

				super.state(context, donacionesCorrectas, "*", "acme.validation.sponsorship.donacionesCorrectas.message");
			}
			{
				boolean soloEuros = true;
				java.util.List<Donation> donations;

				donations = this.repositorio.findDonationsBySponsorshipId(patrocinio.getId());
				if (donations != null) {
					int noEuro = 0;
					for (Donation don : donations)
						if (!don.getMoney().getCurrency().equals("EUR"))
							noEuro += 1;
					soloEuros = noEuro == 0;
				}
				super.state(context, soloEuros, "*", "acme.validation.sponsorship.soloEuros.message");
			}
			result = !super.hasErrors(context);
		}
		return result;
	}

}
