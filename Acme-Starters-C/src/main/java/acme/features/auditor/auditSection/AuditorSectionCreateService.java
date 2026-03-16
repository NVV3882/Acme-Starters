
package acme.features.auditor.auditSection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditReport;
import acme.entities.audit.AuditSection;
import acme.entities.audit.SectionKind;
import acme.realms.Auditor;

@Service
public class AuditorSectionCreateService extends AbstractService<Auditor, AuditSection> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorSectionRepository	repository;

	private AuditSection				auditSection;

	// AbstractService interface ----------------------------------------------


	@Override
	public void load() {
		int reportId = super.getRequest().getData("reportId", int.class);
		AuditReport report = this.repository.findAuditReportById(reportId);

		this.auditSection = super.newObject(AuditSection.class);
		this.auditSection.setReport(report);
	}

	@Override
	public void authorise() {
		super.setAuthorised(this.auditSection.getReport().getDraftMode() && this.auditSection.getReport().getAuditor().isPrincipal());
	}

	@Override
	public void bind() {
		super.bindObject(this.auditSection, "name", "notes", "hours", "kind");
	}

	@Override
	public void validate() {
		super.validateObject(this.auditSection);
	}

	@Override
	public void execute() {
		this.repository.save(this.auditSection);
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		Tuple tuple;

		choices = SelectChoices.from(SectionKind.class, this.auditSection.getKind());

		tuple = super.unbindObject(this.auditSection, "name", "notes", "hours", "kind");
		tuple.put("reportId", this.auditSection.getReport().getId());
		tuple.put("draftMode", this.auditSection.getReport().getDraftMode());
		tuple.put("kinds", choices);
	}

}
