
package acme.features.auditor.auditReport;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditReport;
import acme.entities.audit.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorReportPublishService extends AbstractService<Auditor, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorReportRepository	repository;

	private AuditReport				report;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.report = this.repository.findAuditReportById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.report != null && this.report.getDraftMode() && this.report.getAuditor().isPrincipal();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		if (this.getRequest().getData().containsKey("ticker") || this.getRequest().getData().containsKey("startMoment"))
			super.bindObject(this.report, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {

		boolean requestHasFields = this.getRequest().getData().containsKey("ticker") || this.getRequest().getData().containsKey("startMoment") || this.getRequest().getData().containsKey("endMoment");

		if (requestHasFields)
			super.validateObject(this.report);
		{
			Collection<AuditSection> sections;
			sections = this.repository.findSectionsByAuditReportId(this.report.getId());
			if (sections == null || sections.isEmpty())
				this.state(false, "publish", "auditor.audit-report.form.error.noSections");
		}

		{
			Date start;
			Date end;
			boolean validInterval;

			start = this.report.getStartMoment();
			end = this.report.getEndMoment();
			validInterval = start != null && end != null && MomentHelper.isAfter(end, start);
			if (requestHasFields)
				super.state(validInterval, "startMoment", "acme.validation.sponsorship.dates.error");
			else
				this.state(validInterval, "publish", "acme.validation.sponsorship.dates.error");
		}

		{

			Date now;
			Date start;
			Date end;
			boolean startInFuture;
			boolean endInFuture;

			now = MomentHelper.getCurrentMoment();
			start = this.report.getStartMoment();
			end = this.report.getEndMoment();

			startInFuture = start != null && MomentHelper.isAfter(start, now);
			if (requestHasFields)
				super.state(startInFuture, "startMoment", "acme.validation.audit-report.startMoment.future");
			else
				this.state(startInFuture, "publish", "acme.validation.audit-report.startMoment.future");

			endInFuture = end != null && MomentHelper.isAfter(end, now);
			if (requestHasFields)
				super.state(endInFuture, "endMoment", "acme.validation.audit-report.endMoment.future");
			else
				this.state(endInFuture, "publish", "acme.validation.audit-report.endMoment.future");

		}
	}

	@Override
	public void execute() {
		this.report.setDraftMode(false);
		this.repository.save(this.report);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.report, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "hours");
	}

}
