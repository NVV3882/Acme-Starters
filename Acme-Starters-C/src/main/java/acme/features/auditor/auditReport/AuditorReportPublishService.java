
package acme.features.auditor.auditReport;

import java.time.temporal.ChronoUnit;
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
	}

	@Override
	public void validate() {

		super.validateObject(this.report);

		boolean intervaloCorrectoTiempo;
		Date fechaInicio = this.report.getStartMoment();
		Date fechaFinal = this.report.getEndMoment();
		if (fechaInicio != null && fechaFinal != null)
			intervaloCorrectoTiempo = MomentHelper.computeDifference(fechaInicio, fechaFinal, ChronoUnit.DAYS) >= 1 && MomentHelper.isAfter(fechaFinal, fechaInicio);
		else
			intervaloCorrectoTiempo = true;

		this.state(intervaloCorrectoTiempo, "publish", "auditor.audit-report.form.error.incorrectDates");

		Collection<AuditSection> sections;
		sections = this.repository.findSectionsByAuditReportId(this.report.getId());
		if (sections == null || sections.isEmpty())
			this.state(false, "publish", "auditor.audit-report.form.error.noSections");
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
