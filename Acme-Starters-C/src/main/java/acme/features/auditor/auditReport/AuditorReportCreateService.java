
package acme.features.auditor.auditReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorReportCreateService extends AbstractService<Auditor, AuditReport> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorReportRepository	repository;

	private AuditReport				auditReport;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		Auditor auditor;

		auditor = (Auditor) super.getRequest().getPrincipal().getActiveRealm();

		this.auditReport = super.newObject(AuditReport.class);
		this.auditReport.setDraftMode(true);
		this.auditReport.setAuditor(auditor);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditReport);

		if (!this.getResponse().getErrors().hasErrors()) {
			boolean correctDates = true;
			if (this.auditReport.getStartMoment() != null && this.auditReport.getEndMoment() != null)
				if (MomentHelper.isAfter(this.auditReport.getStartMoment(), this.auditReport.getEndMoment()))
					correctDates = false;
			this.state(correctDates, "endMoment", "auditor.audit-report.form.error.incorrectDates");
		}
	}

	@Override
	public void execute() {
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "hours");
	}

}
