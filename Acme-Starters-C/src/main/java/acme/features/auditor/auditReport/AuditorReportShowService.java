
package acme.features.auditor.auditReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.audit.AuditReport;
import acme.realms.Auditor;

@Service
public class AuditorReportShowService extends AbstractService<Auditor, AuditReport> {

	@Autowired
	private AuditorReportRepository	repository;

	private AuditReport				report;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.report = this.repository.findAuditReportById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		status = this.report != null && (!this.report.getDraftMode() || this.report.getAuditor().isPrincipal());
		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.report, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "hours");
	}
}
