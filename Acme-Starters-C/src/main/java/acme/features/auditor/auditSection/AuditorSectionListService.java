
package acme.features.auditor.auditSection;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audit.AuditReport;
import acme.entities.audit.AuditSection;
import acme.realms.Auditor;

@Service
public class AuditorSectionListService extends AbstractService<Auditor, AuditSection> {

	@Autowired
	private AuditorSectionRepository	repository;
	private AuditReport					auditReport;
	private Collection<AuditSection>	auditSections;


	@Override
	public void authorise() {
		boolean status;
		status = this.auditReport != null && (!this.auditReport.getDraftMode() || this.auditReport.getAuditor().isPrincipal());
		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int reportId = super.getRequest().getData("reportId", int.class);
		this.auditReport = this.repository.findAuditReportById(reportId);
		this.auditSections = this.repository.findSectionsByAuditReportId(reportId);
	}

	@Override
	public void unbind() {
		boolean showCreate;
		super.unbindObjects(this.auditSections, "name", "hours", "kind");

		showCreate = this.auditReport.getDraftMode() && this.auditReport.getAuditor().isPrincipal();
		super.unbindGlobal("reportId", this.auditReport.getId());
		super.unbindGlobal("showCreate", showCreate);
	}
}
