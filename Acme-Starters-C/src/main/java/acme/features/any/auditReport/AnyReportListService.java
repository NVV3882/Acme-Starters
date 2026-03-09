
package acme.features.any.auditReport;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.audit.AuditReport;

public class AnyReportListService extends AbstractService<Any, AuditReport> {

	@Autowired
	AnyReportRepository		repositorio;

	Collection<AuditReport>	auditReports;


	@Override
	public void load() {
		this.auditReports = this.repositorio.listAllAuditReports();
	}

	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.auditReports, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "hours");
	}
}
