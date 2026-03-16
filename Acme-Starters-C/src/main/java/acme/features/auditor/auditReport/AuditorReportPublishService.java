
package acme.features.auditor.auditReport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
