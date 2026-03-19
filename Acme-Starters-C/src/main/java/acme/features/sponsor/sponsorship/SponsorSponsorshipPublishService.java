
package acme.features.sponsor.sponsorship;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Donation;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipPublishService extends AbstractService<Sponsor, Sponsorship> {

	@Autowired
	SponsorSponsorshipRepository	repositorio;

	Sponsorship						patrocinio;


	@Override
	public void load() {
		int sponsorshipId = super.getRequest().getData("id", int.class);
		this.patrocinio = this.repositorio.showSponsorshipBySponsorshipId(sponsorshipId);
	}

	@Override
	public void authorise() {
		boolean res;
		if (this.patrocinio.getDraftMode().equals(true) && this.patrocinio.getSponsor().isPrincipal() && this.patrocinio != null)
			res = true;
		else
			res = false;
		super.setAuthorised(res);
	}
	@Override
	public void bind() {
		super.bindObject(this.patrocinio, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}
	@Override
	public void validate() {

		super.validateObject(this.patrocinio);
		{
			Collection<Donation> donaciones;
			boolean tieneDonaciones;

			donaciones = this.repositorio.findDonationsBySponsorshipId(this.patrocinio.getId());
			tieneDonaciones = donaciones != null && !donaciones.isEmpty();
			super.state(tieneDonaciones, "totalMoney", "acme.validation.sponsorship.donations.error");
		}

		{
			Date start;
			Date end;
			boolean validInterval;

			start = this.patrocinio.getStartMoment();
			end = this.patrocinio.getEndMoment();
			validInterval = start != null && end != null && MomentHelper.isAfter(end, start);
			super.state(validInterval, "startMoment", "acme.validation.sponsorship.dates.error");
		}

		{
			Date now;
			Date start;
			Date end;
			boolean startInFuture;
			boolean endInFuture;

			now = MomentHelper.getCurrentMoment();
			start = this.patrocinio.getStartMoment();
			end = this.patrocinio.getEndMoment();

			startInFuture = start != null && MomentHelper.isAfter(start, now);
			super.state(startInFuture, "startMoment", "acme.validation.sponsorship.startMoment.future");

			endInFuture = end != null && MomentHelper.isAfter(end, now);
			super.state(endInFuture, "endMoment", "acme.validation.sponsorship.endMoment.future");
		}
	}
	@Override
	public void execute() {
		this.patrocinio.setDraftMode(false);
		this.repositorio.save(this.patrocinio);
	}
	@Override
	public void unbind() {
		super.unbindObject(this.patrocinio, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "totalMoney");
	}
}
